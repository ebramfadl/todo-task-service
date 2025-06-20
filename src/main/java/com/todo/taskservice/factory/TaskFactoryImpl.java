package com.todo.taskservice.factory;

import com.todo.taskservice.client.UserClient;
import com.todo.taskservice.dto.TaskCreateDto;
import com.todo.taskservice.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskFactoryImpl implements TaskFactory {
    private final UserClient userClient;

    public TaskFactoryImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public Task createTask(TaskCreateDto taskCreateDto) {
        Task task = new Task();
        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setTaskPriority(taskCreateDto.getTaskPriority());
        task.setTaskStatus(taskCreateDto.getTaskStatus());
        task.setDeadline(taskCreateDto.getDeadline());
        task.setTagName(taskCreateDto.getTagName());
        task.setBoardId(taskCreateDto.getBoardId());
        task.setAssignedUserId(taskCreateDto.getAssignedUserId());
        task.setCreatedBy(userClient.getLoggedInUser());
        return task;
    }
}
