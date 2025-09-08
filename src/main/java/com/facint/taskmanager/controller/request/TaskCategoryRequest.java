package com.facint.taskmanager.controller.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskCategoryRequest {

    private Integer id;

    @NotBlank(message = "{task.category.not-blank}")
    @Size(max = 50, message = "{task.category.size}")
    @Column(length = 50)
    private String name;
}
