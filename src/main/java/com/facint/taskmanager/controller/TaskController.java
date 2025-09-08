package com.facint.taskmanager.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.assembler.TaskModelAssembler;
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

    @Autowired
    private TaskModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<TaskResponse>> retrieveAllTasks(@RequestParam Map<String, String> params) {

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

        List<EntityModel<TaskResponse>> tasksModel = tasks
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(tasksModel, 
            linkTo(methodOn(TaskController.class).retrieveAllTasks(new HashMap<>())).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<TaskResponse> retrieveTaskById(@PathVariable Integer id) {

        Task task = service.retrieveTaskById(id);
        return assembler.toModel(task);
    }

    @PostMapping
    public ResponseEntity<EntityModel<TaskResponse>> saveTask(@Valid @RequestBody TaskRequest taskRequest) {

        Task task = mapper.map(taskRequest, Task.class);
        Task savedTask = service.saveTask(task);
        
        EntityModel<TaskResponse> taskModel = assembler.toModel(savedTask);

        // return mapper.map(savedTask, TaskResponse.class);
        return ResponseEntity
            .created(taskModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(taskModel);
    }

    @DeleteMapping("/{id}")
    public void removeTask(@PathVariable Integer id) {
        service.removeById(id);
    }

    @PutMapping("/{id}/start")
    public EntityModel<TaskResponse> startTask(@PathVariable Integer id) {

        Task task = service.moveTaskToInProgress(id);
        return assembler.toModel(task);
    }

    @PutMapping("/{id}/close")
    public EntityModel<TaskResponse> closeTask(@PathVariable Integer id) {

        Task task = service.moveTaskToDone(id);
        return assembler.toModel(task);
    }

    @PutMapping("/{id}/cancel")
    public EntityModel<TaskResponse> cancelTask(@PathVariable Integer id) {

        Task task = service.cancelTask(id);
        return assembler.toModel(task);
    }
}
