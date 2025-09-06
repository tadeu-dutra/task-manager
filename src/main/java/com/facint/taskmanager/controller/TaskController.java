package com.facint.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.repository.TaskRepository;

@RestController
public class TaskController {
    
    @Autowired
    TaskRepository repository;

    @GetMapping("/tasks")
    public List<Task> allTasks() {
        return repository.findAll();
    }


}
