package com.todo.taskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationSetting {
    private Long userId;
    private String email;
    private Boolean enableNotification;
}
