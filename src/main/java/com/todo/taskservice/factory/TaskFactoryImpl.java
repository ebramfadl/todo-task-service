package com.todo.taskservice.factory;

import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.enums.TaskType;
import com.todo.taskservice.model.Task;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TaskFactoryImpl implements TaskFactory{

    @Override
    public Task createTask(TaskType taskType, String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        task.setTaskStatus(TaskStatus.PENDING);

        switch(taskType) {
            case HIGH_PRIORITY -> task.setTaskPriority(TaskPriority.HIGH);
            case MEDIUM_PRIORITY -> task.setTaskPriority(TaskPriority.MEDIUM);
            case LOW_PRIORITY -> task.setTaskPriority(TaskPriority.LOW);
            default -> task.setTaskPriority(TaskPriority.MEDIUM);
        }
        return task;
    }

    @Override
    public Task createDefaultTask() {
        return createTask(TaskType.MEDIUM_PRIORITY, "Default Task", "Add description here.");
    }
}
