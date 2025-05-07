package com.todo.taskservice.factory;

import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.enums.TaskType;
import com.todo.taskservice.model.Task;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class TaskFactoryImpl implements TaskFactory {

    @Override
    public Task createTask(TaskType taskType, String title, String description, Long createdBy, Long assignedUserId, Long boardId, LocalDateTime deadline, String tagName) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        task.setTaskStatus(TaskStatus.PENDING);
        task.setCreatedBy(createdBy);
        task.setBoardId(boardId);

        if (assignedUserId != null) {
            task.setAssignedUserId(assignedUserId);
        }
        if (deadline != null) {
            task.setDeadline(deadline);
        }
        if (tagName != null) {
            task.setTagName(tagName);
        }

        switch (taskType) {
            case HIGH_PRIORITY -> task.setTaskPriority(TaskPriority.HIGH);
            case MEDIUM_PRIORITY -> task.setTaskPriority(TaskPriority.MEDIUM);
            case LOW_PRIORITY -> task.setTaskPriority(TaskPriority.LOW);
            default -> task.setTaskPriority(TaskPriority.MEDIUM);
        }

        return task;
    }

    @Override
    public Task createDefaultTask(Long createdBy, Long boardId) {
        Task task = new Task();
        task.setTitle("Default Task");
        task.setDescription("Add description here.");
        task.setTaskStatus(TaskStatus.PENDING);
        task.setTaskPriority(TaskPriority.MEDIUM);
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        task.setCreatedBy(createdBy); // from the user microservice
        task.setBoardId(boardId);
        return task;
    }
}
