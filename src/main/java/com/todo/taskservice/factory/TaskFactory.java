package com.todo.taskservice.factory;

import com.todo.taskservice.enums.TaskType;
import com.todo.taskservice.model.Task;

public interface TaskFactory {
    Task createTask(TaskType taskType, String title, String description);
    Task createDefaultTask();
}
