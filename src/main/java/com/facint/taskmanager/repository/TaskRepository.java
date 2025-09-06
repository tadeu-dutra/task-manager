package com.facint.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facint.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> findByDescription(String description);
    public List<Task> findByDescriptionLike(String description);
}
