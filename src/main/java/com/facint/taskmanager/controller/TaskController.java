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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.response.TaskResponse;
import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {
    
    @Autowired
    private TaskService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/tasks")
    public List<TaskResponse> retrieveAllTasks(@RequestParam Map<String, String> params) {

        List<Task> tasks = new ArrayList<>();

        if (params.isEmpty()) {
            tasks = service.retrieveAllTasks();
        } else {
            String description = params.get("description");
            tasks = service.retrieveTasksByDescription(description);
        }

        List<TaskResponse> tasksResponse = tasks.stream().map(t -> mapper.map(t, TaskResponse.class)).collect(Collectors.toList());
        
        return tasksResponse;
    }

    @GetMapping("/tasks/{id}")
    public TaskResponse retrieveTaskById(@PathVariable Integer id) {

        Task task = service.retrieveTaskById(id);
        TaskResponse taskResponse = mapper.map(task, TaskResponse.class);
        
        return taskResponse;
    }

    @PostMapping("/tasks")
    public TaskResponse addTask(@Valid @RequestBody Task task) {
        return mapper.map(service.saveTask(task), TaskResponse.class);
    }

    @DeleteMapping("/tasks/{id}")
    public void removeTask(@PathVariable Integer id) {
        service.removeById(id);
    }
}
