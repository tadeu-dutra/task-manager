package com.facint.taskmanager.controller;

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

import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {
    
    @Autowired
    TaskService service;

    @GetMapping("/tasks")
    public List<Task> retrieveAllTasks(@RequestParam Map<String, String> params) {

        if (params.isEmpty()) {
            return service.retrieveAllTasks();
        }

        String description = params.get("description");
        return service.retrieveTasksByDescription(description);
    }

    @GetMapping("/tasks/{id}")
    public Task retrieveTaskById(@PathVariable Integer id) {
        return service.retrieveTaskById(id);
    }

    @PostMapping("/tasks")
    public Task addTask(@Valid @RequestBody Task task) {
        return service.saveTask(task);
    }

    @DeleteMapping("/tasks/{id}")
    public void removeTask(@PathVariable Integer id) {
        service.removeById(id);
    }
}
