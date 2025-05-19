package com.todo.taskservice.service;

import com.todo.taskservice.client.UserClient;
import com.todo.taskservice.dto.EmailMessage;
import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.dto.UserNotificationSetting;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.factory.TaskFactory;
import com.todo.taskservice.model.Task;
import com.todo.taskservice.rabbitmq.TaskNotificationPublisher;
import com.todo.taskservice.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;
    private final UserClient userClient;
    private final TaskNotificationPublisher taskNotificationPublisher;

    public TaskService(TaskRepository taskRepository, TaskFactory taskFactory, UserClient userClient, TaskNotificationPublisher taskNotificationPublisher) {
        this.taskRepository = taskRepository;
        this.taskFactory = taskFactory;
        this.userClient = userClient;
        this.taskNotificationPublisher = taskNotificationPublisher;
    }
    public Task addTask(TaskCreateDto taskCreateDto){
        log.info("TaskService: inside addTask method");
        return taskRepository.save(taskFactory.createTask(taskCreateDto));
    }
    public Task getById(Long id){
        log.info("TaskService: inside getById method");
        return taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found."));
    }
    public Task updateTask(Long id, Task updatedTask){
        log.info("TaskService: inside updateTask method");
        Task existing = taskRepository.findById(id).orElseThrow(()->new RuntimeException("Task not found."));
        if(updatedTask.getTitle() != null){
            existing.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getDescription()!=null){
            existing.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getTaskStatus()!=null){
            existing.setTaskStatus(updatedTask.getTaskStatus());
        }
        if(updatedTask.getTaskPriority()!=null){
            existing.setTaskPriority(updatedTask.getTaskPriority());
        }
        if(updatedTask.getDeadline()!=null){
            existing.setDeadline(updatedTask.getDeadline());
        }
        if(updatedTask.getTagName()!=null){
            existing.setTagName(updatedTask.getTagName());
        }
        return taskRepository.save(existing);
    }
    public void deleteTask(Long id){
        log.info("TaskService: inside deleteTask method");
        taskRepository.deleteById(id);
    }
    public void assignTaskToUser(Long taskId, Long userId){
        log.info("TaskService: inside assignTaskToUser method");
        Task task = taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task not found."));
        if(!userClient.checkUserExistsById(userId)){
            throw new RuntimeException("Please enter a valid user Id");
        }
        task.setAssignedUserId(userId);
        taskRepository.save(task);
    }
    public void unassignTaskToUser(Long taskId){
        log.info("TaskService: inside unassignTaskToUser method");
        Task task = taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task not found."));
        task.setAssignedUserId(null);
        taskRepository.save(task);
    }
    public List<Task> viewTasksAssignedToUser(Long userId){
        log.info("TaskService: inside viewTasksAssignedToUser method");
        return taskRepository.findByAssignedUserId(userId);
    }
    public List<Task> viewTasksCreatedByUser(Long userId){
        log.info("TaskService: inside viewTasksCreatedByUser method");
        return taskRepository.findByCreatedBy(userId);
    }
    public List<Task> getTasksByBoardId(Long boardId){
        log.info("TaskService: inside getTasksByBoardId method");
        return taskRepository.findByBoardId(boardId);
    }

    public Task assignTag(Long taskId, String tagName) {
        log.info("TaskService: inside assignTag method");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTagName(tagName);
        return taskRepository.save(task);
    }

    public Task unassignTag(Long taskId) {
        log.info("TaskService: inside unassignTag method");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found."));
        task.setTagName(null);
        return taskRepository.save(task);
    }


    public Task markTaskAsCompleted(Long id) {
        log.info("TaskService: inside markTaskAsCompleted method");
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTaskStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }

    public List<Task> searchTasksByTitle(String title) {
        log.info("TaskService: inside searchTasksByTitle method");
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Task> searchTasksByTag(String tag) {
        log.info("TaskService: inside searchTasksByTag method");
        return taskRepository.findByTagNameContainingIgnoreCase(tag);
    }
    public List<Task> filterTasksByPriority(TaskPriority priority) {
        log.info("TaskService: inside filterTasksByPriority method");
        return taskRepository.findByTaskPriority(priority);
    }

    public List<Task> filterTasksByStatus(TaskStatus status) {
        log.info("TaskService: inside filterTasksByStatus method");
        return taskRepository.findByTaskStatus(status);
    }
    public List<Task> getAllTasks() {
        log.info("TaskService: inside getAllTasks method");
        return taskRepository.findAll();
    }
    public List<Task> getTasksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("TaskService: inside getTasksByDateRange method");
        return taskRepository.findByDeadlineBetween(startDate, endDate);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void scheduleDailyNotifications() {
        notifyUsers();
    }

    public void notifyUsers(){
        List<Task> pendingTasks = taskRepository.findByTaskStatus(TaskStatus.PENDING);

        // filter only the tasks of deadline within a week
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekFromNow = now.plusDays(7);

        // get the userIds of tasksWithinAWeek
        List<Long> userIds = pendingTasks.stream()
                .filter(task -> task.getDeadline().isBefore(oneWeekFromNow) && task.getDeadline().isAfter(now))
                .map(Task::getAssignedUserId)
                .distinct()
                .toList();
        log.info("USER IDs: " + userIds);

        // get the setting of these users
        List<UserNotificationSetting> userSettings = userClient.getSettings(userIds);
        log.info("USER SETTINGS: " + userSettings);

        // Get list of emails where notifications are enabled
        List<String> emails = new ArrayList<>();
        for (UserNotificationSetting userSetting : userSettings) {
            if (userSetting.getEnableNotification()) {
                emails.add(userSetting.getEmail());
            }
        }
        log.info("EMAILS: " + emails);

        // Create email messages
        List<EmailMessage> emailMessages = new ArrayList<>();
        for (String email : emails) {
            EmailMessage message = new EmailMessage();
            message.setToEmail(email);
            message.setSubject("Pending Tasks Deadline");
            message.setBody("You have Tasks that are pending and due within a week.");
            emailMessages.add(message);
        }
        log.info("EMAIL MESSAGES: " + emailMessages);

        // Notify observers
        for (EmailMessage message : emailMessages) {
            taskNotificationPublisher.addObserver(message);
        }
        taskNotificationPublisher.notifyObservers();
    }

}
