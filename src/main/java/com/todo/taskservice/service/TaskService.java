package com.todo.taskservice.service;

import com.todo.taskservice.client.UserClient;
import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.factory.TaskFactory;
import com.todo.taskservice.model.Task;
import com.todo.taskservice.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;
    private final UserClient userClient;

    public TaskService(TaskRepository taskRepository, TaskFactory taskFactory, UserClient userClient) {
        this.taskRepository = taskRepository;
        this.taskFactory = taskFactory;
        this.userClient = userClient;
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

}
