package com.facint.taskmanager.controller.request;

import lombok.Data;

@Data
public class LoginRequest {
    
    private String username;
    private String password;
}
