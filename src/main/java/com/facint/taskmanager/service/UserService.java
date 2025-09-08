package com.facint.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facint.taskmanager.model.User;
import com.facint.taskmanager.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;

    public List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    public User retrieveUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<User> retrieveUsersByName(String name) {
        return repository.findByUsernameLike(name);
    }
}
