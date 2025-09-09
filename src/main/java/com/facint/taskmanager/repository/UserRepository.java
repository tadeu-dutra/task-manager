package com.facint.taskmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facint.taskmanager.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByUsernameLike(String name);

    public Optional<User> findByUsername(String username);
}
