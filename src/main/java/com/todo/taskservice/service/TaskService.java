package com.todo.taskservice.service;

import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.factory.TaskFactory;
import com.todo.taskservice.model.Task;
import com.todo.taskservice.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;

    public TaskService(TaskRepository taskRepository, TaskFactory taskFactory) {
        this.taskRepository = taskRepository;
        this.taskFactory = taskFactory;
    }
    public Task addTask(){
        Task task = taskFactory.createDefaultTask();
        return taskRepository.save(task);
    }
    public Task getById(Long id){
        return taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found."));
    }
    public Task updateTask(Long id, Task updatedTask){
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
        existing.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return taskRepository.save(existing);
    }
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }


    public Task markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTaskStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }

    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Task> searchTasksByTag(String tag) {
        return taskRepository.findByTagNameContainingIgnoreCase(tag);
    }

}
