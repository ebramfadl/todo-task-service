package com.todo.taskservice.controller;

import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.model.Task;
import com.todo.taskservice.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping // will get the createdBy from the user microservice
    public Task createTask(@RequestBody TaskCreateDto taskCreateDto){
        return taskService.addTask(taskCreateDto);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,@RequestBody Task updatedTask){
        return taskService.updateTask(id,updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @PutMapping("/assign/{taskId}/{userId}")
    public void assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId){
        taskService.assignTaskToUser(taskId,userId);
    }
    @PutMapping("/unassign/{taskId}")
    public void unassignTaskToUser(@PathVariable Long taskId){
        taskService.unassignTaskToUser(taskId);
    }
    @GetMapping("/tasks/{userId}")
    public List<Task> viewTasksAssignedToUser(@PathVariable Long userId){
        return taskService.viewTasksAssignedToUser(userId);
    }

    @GetMapping("/created-by/{userId}")
    public List<Task> viewTasksCreatedByUser(@PathVariable Long userId){
        return taskService.viewTasksCreatedByUser(userId);
    }

    @GetMapping("/filter")
    public List<Task> filterTasks(
            @RequestParam(value = "priority", required = false) TaskPriority priority,
            @RequestParam(value = "status", required = false) TaskStatus status) {
        if (priority != null && status != null) {
            return taskService.filterTasksByPriorityAndStatus(priority, status);
        } else if (priority != null) {
            return taskService.filterTasksByPriority(priority);
        } else if (status != null) {
            return taskService.filterTasksByStatus(status);
        } else {
            return taskService.getAllTasks();
        }
    }
    @GetMapping("/all")
    public List<Task> getAllTask(){
        return taskService.getAllTasks();
    }
    @GetMapping("/date-range")
    public List<Task> getTasksByDateRange(
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate) {
        return taskService.getTasksByDateRange(startDate, endDate);
    }


}
