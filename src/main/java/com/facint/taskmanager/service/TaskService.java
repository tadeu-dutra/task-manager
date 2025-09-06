package com.facint.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facint.taskmanager.exception.TaskStatusException;
import com.facint.taskmanager.model.Task;
import com.facint.taskmanager.model.TaskStatus;
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

    public Task moveTaskToInProgress(Integer id) {

        Task task = retrieveTaskById(id);

        if (!TaskStatus.TO_DO.equals(task.getStatus())) {
            throw new TaskStatusException();
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        repository.save(task);

        return task;
    }

    public Task moveTaskToDone(Integer id) {
        Task task = retrieveTaskById(id);

        if (TaskStatus.CANCELED.equals(task.getStatus())) {
            throw new TaskStatusException();
        }

        task.setStatus(TaskStatus.DONE);
        repository.save(task);

        return task;
    }

    public Task cancelTask(Integer id) {
        Task task = retrieveTaskById(id);

        if (TaskStatus.DONE.equals(task.getStatus())) {
            throw new TaskStatusException();
        }

        task.setStatus(TaskStatus.CANCELED);
        repository.save(task);

        return task;
    }
}
