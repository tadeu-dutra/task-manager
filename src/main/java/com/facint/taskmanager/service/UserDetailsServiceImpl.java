package com.facint.taskmanager.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facint.taskmanager.model.User;
import com.facint.taskmanager.repository.UserRepository;
import com.facint.taskmanager.security.UserDetailsImpl;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("=== AUTH DEBUG: Loading user: " + username);

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            logger.info("=== AUTH DEBUG: User not found: " + username);
            return new UsernameNotFoundException("Username not found: " + username);
        });

        logger.info("=== AUTH DEBUG: User found: " + user.getUsername());
        logger.info("=== AUTH DEBUG: Password hash: " + user.getPassword());
        logger.info("=== AUTH DEBUG: Roles: " + user.getRoles());

        UserDetails userDetails = UserDetailsImpl.build(user);
        logger.info("=== AUTH DEBUG: Authorities: " + userDetails.getAuthorities());
        logger.info("=== AUTH DEBUG: Account enabled: " + userDetails.isEnabled());
        logger.info("=== AUTH DEBUG: Account non-expired: " + userDetails.isAccountNonExpired());
        logger.info("=== AUTH DEBUG: Credentials non-expired: " + userDetails.isCredentialsNonExpired());
        logger.info("=== AUTH DEBUG: Account non-locked: " + userDetails.isAccountNonLocked());

        return userDetails;
    }
}
