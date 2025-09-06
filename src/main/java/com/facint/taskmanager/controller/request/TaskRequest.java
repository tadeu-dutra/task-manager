package com.facint.taskmanager.controller.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {
    
    private Integer id;

    @NotBlank(message = "{task.description.not-blank}")
    @Size(min = 5, max = 150, message = "{task.description.size}")
    private String description;

    @FutureOrPresent(message = "{task.description.future-or-present}")
    private LocalDate dueDate;

    @NotNull(message = "{task.category.not-null}")
    @Min(value = 1, message = "{task.category.min}")
    private Integer categoryId;
    
    @NotNull(message = "{task.user.not-null}")
    @Min(value = 1, message = "{task.uesr.min}")
    private Integer userId;
}
