package com.facint.taskmanager.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.facint.taskmanager.exception.TaskStatusException;
import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.model.TaskStatus;
import com.facint.taskmanager.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    
    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void shouldNotMoveToDoneIfTaskIsCanceled() {

        Integer dummyId = 9999;
        Task task = new Task();
        task.setId(dummyId);
        task.setDescription("Dummy Test");
        task.setStatus(TaskStatus.CANCELED);

        Mockito.when(repository.findById(dummyId)).thenReturn(Optional.of(task));

        Assertions.assertThrows(TaskStatusException.class, () -> service.moveTaskToDone(dummyId));
    }

    void shouldNotCancelIfTaskIsDone() {

        Integer dummyId = 1;
        Task task = new Task();
        task.setId(dummyId);
        task.setDescription("Dummy Test");
        task.setStatus(TaskStatus.DONE);

        Mockito.when(repository.findById(dummyId)).thenReturn(Optional.of(task));

        Assertions.assertThrows(TaskStatusException.class, () -> service.cancelTask(dummyId));
    }
}
