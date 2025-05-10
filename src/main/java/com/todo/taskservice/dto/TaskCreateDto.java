package com.todo.taskservice.dto;

import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskCreateDto {
    @NotBlank
    private String title;
    private String description;
    private TaskPriority taskPriority = TaskPriority.MEDIUM;
    private TaskStatus taskStatus = TaskStatus.PENDING;
    @NotBlank
    private LocalDateTime deadline;
    private String tagName;
    private Long boardId;
    private Long assignedUserId;
}
