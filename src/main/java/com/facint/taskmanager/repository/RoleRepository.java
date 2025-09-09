package com.facint.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facint.taskmanager.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
