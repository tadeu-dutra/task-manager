package com.facint.task_manager.model;

import java.time.LocalDate;

import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class Task {
    
    private String description;

    private TaskStatus status;

    private LocalDate dueDate;

    private boolean isVisible;
    
    @ManyToOne
    private TaskCategory category;

    @ManyToOne
    private User user;

}
