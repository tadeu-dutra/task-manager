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
import com.facint.taskmanager.controller.response.TaskCategoryResponse;
import com.facint.taskmanager.model.TaskCategory;

@Component
public class TaskCategoryModelAssembler implements RepresentationModelAssembler<TaskCategory, EntityModel<TaskCategoryResponse>> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public EntityModel<TaskCategoryResponse> toModel(TaskCategory category) {

        TaskCategoryResponse categoryResponse = mapper.map(category, TaskCategoryResponse.class);

        EntityModel<TaskCategoryResponse> categoryModel = EntityModel.of(categoryResponse, 
            linkTo(methodOn(TaskCategoryController.class).retrieveCategoryById(categoryResponse.getId())).withSelfRel(),
            linkTo(methodOn(TaskCategoryController.class).retrieveAllCategories(new HashMap<>())).withRel("categories"));

        return categoryModel;
    }
}
