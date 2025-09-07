package com.facint.taskmanager.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.facint.taskmanager.model.Task;

@SpringBootTest
public class TaskRepositoryTest {
    
    @Autowired
    private TaskRepository repository;

    @Test
    void testFindByCategoryUsingQuery() {
        List<Task> tasks = repository.findByCategoryUsingQuery("Study");
        Assertions.assertEquals(2, tasks.size());
    }

    @Test
    void testFindByCategoryUsingNamedQuery() {
        List<Task> tasks = repository.findByCategoryUsingNamedQuery("Study");
        Assertions.assertEquals(2, tasks.size());
    }

    @Test
    void testFindByUser() {
        List<Task> tasks = repository.findByUser("tadeu");
        
        // Print task information using streams
        tasks.stream()
        .forEach(task -> System.out.println("Task ID: " + task.getId() +
        ", Description: " + task.getDescription() +
        ", Status: " + task.getStatus() +
        ", Due Date: " + task.getDueDate() +
        ", Visible: " + task.isVisible()));
        
        Assertions.assertEquals(2, tasks.size());
    }
}
