package com.facint.taskmanager.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;

import com.facint.taskmanager.controller.response.TaskResponse;
import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.model.TaskCategory;
import com.facint.taskmanager.model.TaskStatus;
import com.facint.taskmanager.model.User;
import com.facint.taskmanager.service.TaskService;

@SpringBootTest
public class TaskControllerTest {

    @Autowired
    private TaskController controller;

    @Mock
    private TaskService service;

    @Test
    void validateTaskResponse() {
        int taskId = 1;
        int userId = 1;
        int categoryId = 1;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(TaskStatus.TO_DO);

        User user = new User();
        user.setId(userId);
        task.setUser(user);

        TaskCategory category = new TaskCategory();
        category.setId(categoryId);
        task.setCategory(category);

        Mockito.when(service.retrieveTaskById(taskId)).thenReturn(task);

        EntityModel<TaskResponse> taskModel = controller.retrieveTaskById(taskId);
        TaskResponse taskResponse = taskModel.getContent();

        Assertions.assertEquals(taskId, taskResponse.getId());
        Assertions.assertEquals(userId, taskResponse.getUserId());
        Assertions.assertEquals(categoryId, taskResponse.getCategoryId());
        Assertions.assertEquals(TaskStatus.TO_DO.name(), taskResponse.getStatus());
    }
}
