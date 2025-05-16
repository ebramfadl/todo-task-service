package com.todo.taskservice.controller;

import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.model.Task;
import com.todo.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    public TaskService getTaskService(){
        return taskService;
    }
    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateDto taskCreateDto){
        return ResponseEntity.ok(getTaskService().addTask(taskCreateDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(getTaskService().getById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task updatedTask){
        return ResponseEntity.ok(getTaskService().updateTask(id,updatedTask));
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        getTaskService().deleteTask(id);
    }

    @PutMapping("/assign/{taskId}/{userId}")
    public void assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId){
        taskService.assignTaskToUser(taskId,userId);
    }
    @PutMapping("/unassign/{taskId}")
    public void unassignTaskToUser(@PathVariable Long taskId){
        getTaskService().unassignTaskToUser(taskId);
    }
    @GetMapping("/tasks/{userId}")
    public ResponseEntity<List<Task>> viewTasksAssignedToUser(@PathVariable Long userId){
        return ResponseEntity.ok(getTaskService().viewTasksAssignedToUser(userId));
    }
    @GetMapping("/created-by/{userId}")
    public ResponseEntity<List<Task>> viewTasksCreatedByUser(@PathVariable Long userId){
        return ResponseEntity.ok(getTaskService().viewTasksCreatedByUser(userId));
    }
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<Task>> getTasksByBoardId(@PathVariable Long boardId){
        return ResponseEntity.ok(getTaskService().getTasksByBoardId(boardId));
    }

    @PatchMapping("/assign-tag/{id}")
    public Task assignTag(@PathVariable Long id, @RequestParam String tag) {
        return taskService.assignTag(id, tag);
    }

    @PatchMapping("/unassign-tag/{id}")
    public Task unassignTag(@PathVariable Long id) {
        return taskService.unassignTag(id);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Task> markTaskCompleted(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.markTaskAsCompleted(id));
    }
    @GetMapping("/search-by-title")
    public ResponseEntity<List<Task>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(taskService.searchTasksByTitle(title));
    }

    @GetMapping("/search-by-tag")
    public ResponseEntity<List<Task>> searchByTag(@RequestParam String tag) {
        return ResponseEntity.ok(taskService.searchTasksByTag(tag));
    }
    @GetMapping("/priority/{priority}")
    public List<Task> filterTasksByPriority(@PathVariable TaskPriority priority) {
        return taskService.filterTasksByPriority(priority);
    }

    @GetMapping("/status/{status}")
    public List<Task> filterTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.filterTasksByStatus(status);
    }

    @GetMapping("/all")
    public List<Task> getAllTask(){
        return taskService.getAllTasks();
    }
    @GetMapping("/from/{startDate}/to/{endDate}")
    public List<Task> getTasksByDateRange(
            @PathVariable LocalDateTime startDate,
            @PathVariable LocalDateTime endDate) {
        return taskService.getTasksByDateRange(startDate, endDate);
    }

}
