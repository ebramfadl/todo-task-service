package com.todo.taskservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailMessage implements Serializable {
    private String toEmail;
    private String subject;
    private String body;
}
