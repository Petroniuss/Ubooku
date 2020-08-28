package com.agh.iet.ubooku.controller;

import com.agh.iet.ubooku.exception.ResourceNotFoundException;
import com.agh.iet.ubooku.model.auth.User;
import com.agh.iet.ubooku.repository.UserRepository;
import com.agh.iet.ubooku.security.CurrentUser;
import com.agh.iet.ubooku.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

}
