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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.assembler.UserModelAssembler;
import com.facint.taskmanager.controller.request.UserRequest;
import com.facint.taskmanager.controller.response.UserResponse;
import com.facint.taskmanager.model.User;
import com.facint.taskmanager.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserModelAssembler assembler;


    @GetMapping
    public CollectionModel<EntityModel<UserResponse>> retrieveAllUsers(@RequestParam Map<String, String> params) {

        
        List<User> users = new ArrayList<>();
        
        if (params.isEmpty()) {
            users = service.retrieveAllUsers();
        } else {
            String name = params.get("name");
            users = service.retrieveUsersByName(name);
        }

        List<EntityModel<UserResponse>> usersModel = users
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(usersModel,
            linkTo(methodOn(UserController.class).retrieveAllUsers(new HashMap<>())).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<UserResponse> retrieveUserById(@PathVariable Integer id) {

        User user = service.retrieveUserById(id);
        EntityModel<UserResponse> userResponse = assembler.toModel(user);

        return userResponse;
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserResponse>> saveUser(@Valid @RequestBody UserRequest request) {

        User user = mapper.map(request, User.class);
        User savedUser = service.saveUser(user);
        EntityModel<UserResponse> userModel = assembler.toModel(savedUser);

        return ResponseEntity
            .created(userModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(userModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponse>> updateUser(
        @PathVariable Integer id, 
        @Valid @RequestBody UserRequest request) {

        User user = mapper.map(request, User.class);
        User savedUser = service.updateUser(id, user);
        EntityModel<UserResponse> userModel = assembler.toModel(savedUser);
            
        return ResponseEntity.ok().body(userModel);
    }
    

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
