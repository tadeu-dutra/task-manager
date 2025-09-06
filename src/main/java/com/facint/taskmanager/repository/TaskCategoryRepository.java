package com.facint.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facint.taskmanager.model.TaskCategory;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Integer> {

}
