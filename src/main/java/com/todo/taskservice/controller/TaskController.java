package com.todo.taskservice.controller;

import com.todo.taskservice.dto.TaskCreateDto;
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
    @PostMapping("/create-task")
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
    @GetMapping("/board/{boardId}")
    public List<Task> getTasksByBoardId(@PathVariable Long boardId){
        return taskService.getTasksByBoardId(boardId);
    }

    @PatchMapping("/assign-tag/{id}")
    public Task assignTag(@PathVariable Long id, @RequestParam String tag) {
        return taskService.assignTag(id, tag);
    }

    @PatchMapping("/unassign-tag/{id}")
    public Task unassignTag(@PathVariable Long id) {
        return taskService.unassignTag(id);
    }

}
