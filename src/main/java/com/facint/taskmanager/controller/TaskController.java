package com.facint.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.repository.TaskRepository;

@RestController
public class TaskController {
    
    @Autowired
    TaskRepository repository;

    @GetMapping("/tasks")
    public List<Task> retrieveAllTasks() {
        return repository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task retrieveTaskById(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {
        return repository.save(task);
    }

    @DeleteMapping("/tasks/{id}")
    public void removeTask(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
