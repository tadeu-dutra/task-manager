package com.facint.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facint.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
