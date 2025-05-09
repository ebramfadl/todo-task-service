package com.todo.taskservice.dto;

import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskCreateDto {
    private String title;
    private String description;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private LocalDateTime deadline;
    private String tagName;
    private Long boardId;
    private Long assignedUserId;
}
