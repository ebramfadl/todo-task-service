package com.todo.taskservice.repository;

import com.todo.taskservice.enums.TaskPriority;
import com.todo.taskservice.enums.TaskStatus;
import com.todo.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    public List<Task> findByAssignedUserId(Long userId);
    public List<Task> findByCreatedBy(Long userId);
    public List<Task> findByBoardId(Long boardId);

    List<Task> findByTitleContainingIgnoreCase(String title);
    List<Task> findByTagNameContainingIgnoreCase(String tagName);
    public List<Task> findByTaskPriority(TaskPriority priority);
    public List<Task> findByTaskStatus(TaskStatus status);
    List<Task> findByDeadlineBetween(LocalDateTime startDate, LocalDateTime endDate);
}


