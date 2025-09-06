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
    void testFindByCategoryName() {
        List<Task> tasks = repository.findByCategoryName("Study");
        Assertions.assertEquals(2, tasks.size());
    }
}
