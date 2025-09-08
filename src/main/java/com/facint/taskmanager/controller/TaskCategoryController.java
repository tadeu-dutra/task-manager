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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.assembler.TaskCategoryModelAssembler;
import com.facint.taskmanager.controller.request.TaskCategoryRequest;
import com.facint.taskmanager.controller.response.TaskCategoryResponse;
import com.facint.taskmanager.model.TaskCategory;
import com.facint.taskmanager.service.TaskCategoryService;

@RestController
@RequestMapping("/categories")
public class TaskCategoryController {
    
    @Autowired
    private TaskCategoryService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TaskCategoryModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<TaskCategoryResponse>> retrieveAllCategories(@RequestParam Map<String, String> params) {

        List<TaskCategory> categories = new ArrayList<>();

        if (params.isEmpty()) {
            categories = service.retrieveAllCategories();
        } else {
            String name = params.get("name");
            categories = service.retrieveCategoriesByName(name);
        }

        List<EntityModel<TaskCategoryResponse>> categoryModel = categories
            .stream()
            // .map(c -> mapper.map(c, TaskCategoryResponse.class))
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(categoryModel,
            linkTo(methodOn(TaskCategoryController.class).retrieveAllCategories(new HashMap<>())).withSelfRel());
            
    }

    @GetMapping("/{id}")
    public EntityModel<TaskCategoryResponse> retrieveCategoryById(@PathVariable Integer id) {

        return assembler.toModel(service.retrieveCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<TaskCategoryResponse>> saveCategory(@RequestBody TaskCategoryRequest categoryRequest) {

        TaskCategory category = mapper.map(categoryRequest, TaskCategory.class);

        EntityModel<TaskCategoryResponse> entityModel = assembler.toModel(service.saveCategory(category));
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable Integer id) {
        service.removeById(id);
    }
}
