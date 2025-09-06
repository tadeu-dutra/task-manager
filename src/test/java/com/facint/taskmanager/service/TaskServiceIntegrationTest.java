package com.facint.taskmanager.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.facint.taskmanager.exception.TaskStatusException;
import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.model.TaskStatus;

@SpringBootTest
public class TaskServiceIntegrationTest {
    
    @Autowired
    private TaskService service;

    @Test
    void shouldMoveTaskToInProgress() {
        Task task = service.moveTaskToInProgress(1);
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void shoudNotMoveTaskToInProgress() {
        Task task = service.retrieveTaskById(1);
        task.setStatus(TaskStatus.DONE);
        service.saveTask(task);
        Assertions.assertEquals(TaskStatus.DONE, task.getStatus());
        Assertions.assertThrows(TaskStatusException.class, () -> service.moveTaskToInProgress(1));
    }
}
