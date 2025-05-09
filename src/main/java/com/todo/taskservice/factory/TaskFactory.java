package com.todo.taskservice.factory;

import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.model.Task;

public interface TaskFactory {
    Task createTask(TaskCreateDto taskCreateDto, Long createdBy);
}
