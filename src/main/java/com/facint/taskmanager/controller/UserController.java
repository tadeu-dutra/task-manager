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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.assembler.UserModelAssembler;
import com.facint.taskmanager.controller.response.UserResponse;
import com.facint.taskmanager.model.User;
import com.facint.taskmanager.service.UserService;

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
        // UserResponse userResponse = mapper.map(user, UserResponse.class);
        // return userResponse;

        return assembler.toModel(user);
    }

    // @PostMapping
    // public UserResponse saveUser(@Valid @RequestBody UserRequest userRequest) {

    //     User user = mapper.map(userRequest, User.class);

    //     return mapper.map(service.saveUser(user), UserResponse.class);
    // }

    // @DeleteMapping("/{id}")
    // public void removeUser(@PathVariable Integer id) {
    //     service.removeById(id);
    // }
}
