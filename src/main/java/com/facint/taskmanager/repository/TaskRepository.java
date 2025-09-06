package com.facint.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facint.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> findByDescription(String description);
    public List<Task> findByDescriptionLike(String description);

    @Query("select t from Task t inner join t.category c where c.name = ?1")
    public List<Task> findByCategoryName(String categoryName);
}
