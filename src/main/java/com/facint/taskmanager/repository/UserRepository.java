package com.facint.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facint.taskmanager.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
