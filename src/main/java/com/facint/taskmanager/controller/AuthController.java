package com.facint.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facint.taskmanager.controller.request.LoginRequest;
import com.facint.taskmanager.controller.response.JwtResponse;
import com.facint.taskmanager.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        JwtResponse jwtResponse = userService.authenticateUser(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(jwtResponse);
    }
}
