package com.facint.taskmanager.config;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.facint.taskmanager.model.Role;
import com.facint.taskmanager.model.RoleName;
import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.model.TaskCategory;
import com.facint.taskmanager.model.TaskStatus;
import com.facint.taskmanager.model.User;
import com.facint.taskmanager.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Bean
    CommandLineRunner run() {
        return args -> {

            Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
            roleAdmin = roleRepository.save(roleAdmin);

            User user = new User();
            user.setUsername("tadeu");
            user.setPassword(encoder.encode("1"));
            user.setRoles(Set.of(roleAdmin));
            userRepository.save(user);

            TaskCategory category = new TaskCategory();
            category.setName("Study");
            categoryRepository.save(category);

            Task task = new Task();
            task.setDescription("Elasticsearch On-Demand");
            task.setDueDate(LocalDate.now().plusDays(90));
            task.setStatus(TaskStatus.TO_DO);
            task.setVisible(true);
            task.setCategory(category);
            task.setUser(user);
            taskRepository.save(task);

            Task task2 = new Task();
            task2.setDescription("Kibana Data & Analytics");
            task2.setDueDate(LocalDate.now().plusDays(90));
            task2.setStatus(TaskStatus.DONE);
            task2.setVisible(true);
            task2.setCategory(category);
            task2.setUser(user);
            taskRepository.save(task2);
        };
    }
}
