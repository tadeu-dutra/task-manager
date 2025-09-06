package com.facint.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> retrieveAllTasks() {
        return repository.findAll();
    }

    public List<Task> retrieveTasksByDescription(String description) {
        return repository.findByDescriptionLike(description);
    }

    public Task retrieveTaskById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Task saveTask(Task task) {
        return repository.save(task);
    }

    public void removeById(Integer id) {
        repository.deleteById(id);
    }
}
