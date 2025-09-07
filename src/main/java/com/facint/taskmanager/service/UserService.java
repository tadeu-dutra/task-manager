package com.facint.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facint.taskmanager.model.User;
import com.facint.taskmanager.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;

    public User retrieveUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }
}
