package com.facint.taskmanager.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
@NamedQuery(name = "Task.findByCategoryUsingNamedQuery", query = "select t from Task t inner join t.category c where c.name = ?1")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "{task.description.not-blank}")
    @Size(min = 5, max = 150, message = "{task.description.size}")
    @Column(nullable = false, length = 150)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @FutureOrPresent(message = "{task.description.future-or-present}")
    private LocalDate dueDate;

    private boolean isVisible;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private TaskCategory category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

}
