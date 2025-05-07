package com.todo.taskservice.controller;

import com.todo.taskservice.model.Task;
import com.todo.taskservice.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{boardId}") // will get the createdBy from the user microservice
    public Task createTask(Long createdBy, @PathVariable Long boardId){
        return taskService.addTask(createdBy,boardId);
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
    @PutMapping("/unassign/{taskId}/{userId}")
    public void unassignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId){
        taskService.unassignTaskToUser(taskId,userId);
    }
    @GetMapping("/viewTasksOfUser/{userId}")
    public List<Task> viewTasksAssignedToUser(@PathVariable Long userId){
        return taskService.viewTasksAssignedToUser(userId);
    }

    @GetMapping("/viewTasksCreatedByUser/{userId}")
    public List<Task> viewTasksCreatedByUser(@PathVariable Long userId){
        return taskService.viewTasksCreatedByUser(userId);
    }

}
