package com.facint.taskmanager.controller.response;

import java.util.List;

import com.facint.taskmanager.model.RoleName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private List<String> roles;

    public boolean isAdmin() {
        return roles.contains(RoleName.ROLE_ADMIN.name());
    }

    public JwtResponse(String token, Integer id, String username, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
