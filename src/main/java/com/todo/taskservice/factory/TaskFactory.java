package com.todo.taskservice.factory;

import com.todo.taskservice.enums.TaskType;
import com.todo.taskservice.model.Task;

import java.time.LocalDateTime;

public interface TaskFactory {
    Task createTask(TaskType taskType, String title, String description, Long createdBy, Long assignedUserId, Long boardId, LocalDateTime deadline, String tagName);
    Task createDefaultTask(Long createdBy, Long boardId);
}
