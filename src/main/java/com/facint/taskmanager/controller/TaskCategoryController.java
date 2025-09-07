package com.facint.taskmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.request.TaskCategoryRequest;
import com.facint.taskmanager.controller.response.TaskCategoryResponse;
import com.facint.taskmanager.model.TaskCategory;
import com.facint.taskmanager.service.TaskCategoryService;

@RestController
@RequestMapping("/category")
public class TaskCategoryController {
    
    @Autowired
    private TaskCategoryService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<TaskCategoryResponse> retrieveAllCategories(@RequestParam Map<String, String> params) {

        List<TaskCategory> categories = new ArrayList<>();

        if (params.isEmpty()) {
            categories = service.retrieveAllCategories();
        } else {
            String name = params.get("name");
            categories = service.retrieveCategoriesByName(name);
        }

        return categories
            .stream()
            .map(c -> mapper.map(c, TaskCategoryResponse.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskCategoryResponse retrieveCategoryById(@PathVariable Integer id) {

        return mapper.map(service.retrieveCategoryById(id), TaskCategoryResponse.class);
    }

    @PostMapping
    public TaskCategoryResponse saveCategory(@RequestBody TaskCategoryRequest categoryRequest) {

        TaskCategory category = mapper.map(categoryRequest, TaskCategory.class);
        return mapper.map(service.saveCategory(category), TaskCategoryResponse.class);
    }

    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable Integer id) {
        service.removeById(id);
    }
}
