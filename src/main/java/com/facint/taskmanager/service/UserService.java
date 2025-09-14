package com.facint.taskmanager.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.facint.taskmanager.controller.response.JwtResponse;
import com.facint.taskmanager.model.Role;
import com.facint.taskmanager.model.User;
import com.facint.taskmanager.repository.RoleRepository;
import com.facint.taskmanager.repository.UserRepository;
import com.facint.taskmanager.security.JwtUtils;
import com.facint.taskmanager.security.UserDetailsImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public User retrieveUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<User> retrieveUsersByName(String name) {
        return userRepository.findByUsernameLike(name);
    }

    public User saveUser(User user) {

        Set<Role> roles = getRoles(user);
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private Set<Role> getRoles(User user) {

        Set<Role> rolesFromDatabase = user.getRoles()
            .stream()
            .map(r -> roleRepository.findByName(r.getName()))
            .collect(Collectors.toSet());
        
        return rolesFromDatabase;
    }

    public User updateUser(Integer id, User user) {

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        user.setId(id);

        return saveUser(user);
    }

    public void deleteById(Integer id) {

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        
        userRepository.deleteById(id);
    }

    public JwtResponse authenticateUser(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
            .map(i -> i.getAuthority())
            .collect(Collectors.toList());

        return new JwtResponse(jwtToken, userDetails.getId(), userDetails.getUsername(), roles);
    }
}
