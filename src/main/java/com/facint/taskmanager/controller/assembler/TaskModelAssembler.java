package com.facint.taskmanager.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.facint.taskmanager.controller.TaskCategoryController;
import com.facint.taskmanager.controller.TaskController;
import com.facint.taskmanager.controller.UserController;
import com.facint.taskmanager.controller.response.TaskResponse;
import com.facint.taskmanager.model.Task;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<TaskResponse>> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public EntityModel<TaskResponse> toModel(Task task) {

        TaskResponse taskResponse = mapper.map(task, TaskResponse.class);

        EntityModel<TaskResponse> taskModel = EntityModel.of(taskResponse, 
            linkTo(methodOn(TaskController.class).retrieveTaskById(taskResponse.getId())).withSelfRel(),
            linkTo(methodOn(TaskController.class).retrieveAllTasks(new HashMap<>())).withRel("tasks"),
            linkTo(methodOn(TaskCategoryController.class).retrieveCategoryById(taskResponse.getCategoryId())).withRel("category"),
            linkTo(methodOn(UserController.class).retrieveUserById(taskResponse.getUserId())).withRel("user"));
        
        return taskModel;
    }
}
