package com.facint.taskmanager.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.request.TaskRequest;
import com.facint.taskmanager.controller.response.TaskResponse;
import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private TaskService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<TaskResponse> retrieveAllTasks(@RequestParam Map<String, String> params) {

        List<Task> tasks = new ArrayList<>();

        if (params.isEmpty()) {
            tasks = service.retrieveAllTasks();
        } else {
            if (params.containsKey("username")) {
                tasks = service.retrieveTaskByUser(params.get("username"));
            } else if (params.containsKey("description")) {
                tasks = service.retrieveTasksByDescription(params.get("description"));
            }
        }

        List<TaskResponse> tasksResponse = tasks.stream().map(t -> mapper.map(t, TaskResponse.class)).collect(Collectors.toList());
        
        return tasksResponse;
    }

    @GetMapping("/{id}")
    public EntityModel<TaskResponse> retrieveTaskById(@PathVariable Integer id) {

        Task task = service.retrieveTaskById(id);
        TaskResponse taskResponse = mapper.map(task, TaskResponse.class);

        EntityModel<TaskResponse> taskModel = EntityModel.of(taskResponse, linkTo(methodOn(TaskController.class).retrieveTaskById(id)).withSelfRel());
        
        return taskModel;
    }

    @PostMapping
    public TaskResponse addTask(@Valid @RequestBody TaskRequest taskRequest) {

        Task task = mapper.map(taskRequest, Task.class);

        return mapper.map(service.saveTask(task), TaskResponse.class);
    }

    @DeleteMapping("/{id}")
    public void removeTask(@PathVariable Integer id) {
        service.removeById(id);
    }
}
