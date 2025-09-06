package com.facint.taskmanager.controller.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskRequest {
    
    private Integer id;
    private String description;
    private LocalDate dueDate;
    private Integer categoryId;
    private Integer userId;
}
