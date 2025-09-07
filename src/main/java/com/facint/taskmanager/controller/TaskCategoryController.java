package com.facint.taskmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.model.TaskCategory;
import com.facint.taskmanager.service.TaskCategoryService;

@RestController
public class TaskCategoryController {
    
    @Autowired
    private TaskCategoryService service;

    @GetMapping("/category")
    public List<TaskCategory> retrieveAllCategories(@RequestParam Map<String, String> params) {

        List<TaskCategory> categories = new ArrayList<>();

        if (params.isEmpty()) {
            categories = service.retrieveAllCategories();
        } else {
            String name = params.get("name");
            categories = service.retrieveCategoriesByName(name);
        }

        return categories;
    }

    @GetMapping("/category/{id}")
    public TaskCategory retrieveCategoryById(@PathVariable Integer id) {

        return service.retrieveCategoryById(id);
    }

    @PostMapping("/category")
    public TaskCategory saveCategory(@RequestBody TaskCategory category) {

        return service.saveCategory(category);
    }

    @DeleteMapping("/category/{id}")
    public void removeCategory(@PathVariable Integer id) {
        service.removeById(id);
    }
}
