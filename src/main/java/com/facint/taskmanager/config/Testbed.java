package com.facint.taskmanager.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.model.TaskCategory;
import com.facint.taskmanager.model.TaskStatus;
import com.facint.taskmanager.model.User;
import com.facint.taskmanager.repository.TaskCategoryRepository;
import com.facint.taskmanager.repository.TaskRepository;
import com.facint.taskmanager.repository.UserRepository;

@Configuration
@Profile("dev")
public class Testbed {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCategoryRepository categoryRepository;

    @Bean
    CommandLineRunner run() {
        return args -> {
            User user = new User();
            user.setUsername("tadeu");
            user.setPassword("1");
            userRepository.save(user);

            TaskCategory category = new TaskCategory();
            category.setName("Study");
            categoryRepository.save(category);

            Task task = new Task();
            task.setDescription("Elasticsearch On-Demand");
            task.setDueDate(LocalDate.now().plusDays(90));
            task.setStatus(TaskStatus.DONE);
            task.setVisible(true);
            task.setCategory(category);
            task.setUser(user);
            taskRepository.save(task);
        };
    }
}
