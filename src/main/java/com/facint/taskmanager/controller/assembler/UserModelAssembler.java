package com.facint.taskmanager.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.facint.taskmanager.controller.UserController;
import com.facint.taskmanager.controller.response.UserResponse;
import com.facint.taskmanager.model.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<UserResponse>> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public EntityModel<UserResponse> toModel(User user) {

        UserResponse userResponse = mapper.map(user, UserResponse.class);

        EntityModel<UserResponse> userModel = EntityModel.of(userResponse, 
            linkTo(methodOn(UserController.class).retrieveUserById(userResponse.getId())).withSelfRel(),
            linkTo(methodOn(UserController.class).retrieveAllUsers(new HashMap<>())).withRel("users"));

        return userModel;
    }
}
