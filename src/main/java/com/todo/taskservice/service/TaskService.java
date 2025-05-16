package com.todo.taskservice.service;

import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.factory.TaskFactory;
import com.todo.taskservice.model.Task;
import com.todo.taskservice.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskService {


    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;

    public TaskService(TaskRepository taskRepository, TaskFactory taskFactory) {
        this.taskRepository = taskRepository;
        this.taskFactory = taskFactory;
    }
    @Transactional
    public Task addTask(TaskCreateDto taskCreateDto){
        log.info("TaskService: inside addTask method");
       return taskRepository.save(taskFactory.createTask(taskCreateDto));
//        return taskFactory.createTask(taskCreateDto);
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
    @Transactional
    public void deleteTask(Long id){
        log.info("TaskService: inside deleteTask method");
        taskRepository.deleteById(id);
    }
    public void assignTaskToUser(Long taskId, Long userId){
        log.info("TaskService: inside assignTaskToUser method");
        Task task = taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task not found."));
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
    public List<Task> filterTasksByPriority(TaskPriority priority) {
        return taskRepository.findByTaskPriority(priority);
    }

    public List<Task> filterTasksByStatus(TaskStatus status) {
        return taskRepository.findByTaskStatus(status);
    }


    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Task> getTasksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByDeadlineBetween(startDate, endDate);
    }
}
