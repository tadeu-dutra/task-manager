package com.facint.taskmanager.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public UserResponse retrieveUserById(@PathVariable Integer id) {
        User user = service.retrieveUserById(id);
        UserResponse userResponse = mapper.map(user, UserResponse.class);

        return userResponse;
    }
}
