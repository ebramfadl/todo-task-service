package com.todo.taskservice.repository;

import com.todo.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    public List<Task> findByAssignedUserId(Long userId);
    public List<Task> findByCreatedBy(Long userId);

}
