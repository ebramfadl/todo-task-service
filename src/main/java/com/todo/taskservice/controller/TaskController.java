package com.todo.taskservice.controller;

import com.todo.taskservice.model.Task;
import com.todo.taskservice.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(){
        return taskService.addTask();
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


}
