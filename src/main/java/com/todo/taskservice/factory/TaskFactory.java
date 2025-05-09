package com.todo.taskservice.factory;

import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.enums.TaskType;
import com.todo.taskservice.model.Task;

import java.time.LocalDateTime;

public interface TaskFactory {
    Task createTask(TaskCreateDto taskCreateDto, Long createdBy);
}
