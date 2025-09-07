package com.facint.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facint.taskmanager.model.TaskCategory;
import com.facint.taskmanager.repository.TaskCategoryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskCategoryService {

    @Autowired
    private TaskCategoryRepository repository;

    public List<TaskCategory> retrieveAllCategories() {
        return repository.findAll();
    }

    public List<TaskCategory> retrieveCategoriesByName(String name) {
        return repository.findByNameLike(name);
    }

    public TaskCategory retrieveCategoryById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public TaskCategory saveCategory(TaskCategory category) {
        return repository.save(category);
    }

    public void removeById(Integer id) {
        repository.deleteById(id);
    }
}
