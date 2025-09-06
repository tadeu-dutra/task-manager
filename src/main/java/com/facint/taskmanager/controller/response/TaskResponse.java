package com.facint.taskmanager.controller.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskResponse {
    
    private Integer id;
    private String description;
    private String status;
    private LocalDate dueDate;
    private Integer categoryId;
    private Integer userId;
}
