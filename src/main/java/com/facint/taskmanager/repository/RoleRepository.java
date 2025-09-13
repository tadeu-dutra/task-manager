package com.facint.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facint.taskmanager.model.Role;
import com.facint.taskmanager.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(RoleName name);
}
