package com.facint.taskmanager.controller.request;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    private Integer id;

    @NotBlank(message = "{user.name.not-blank}")
    @Size(max = 100, message = "{user.name.size}")
    @Column(length = 100)
    private String username;

    @NotBlank(message = "{user.password.not-blank}")
    @Column(nullable = false)
    private String password;

    private Set<RoleRequest> roles;
}
