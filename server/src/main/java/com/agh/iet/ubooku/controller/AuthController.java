package com.agh.iet.ubooku.controller;

import com.agh.iet.ubooku.model.auth.AuthProvider;
import com.agh.iet.ubooku.model.auth.Role;
import com.agh.iet.ubooku.model.auth.RoleName;
import com.agh.iet.ubooku.model.auth.User;
import com.agh.iet.ubooku.payload.ApiResponse;
import com.agh.iet.ubooku.payload.auth.JwtAuthenticationResponse;
import com.agh.iet.ubooku.payload.auth.LoginRequest;
import com.agh.iet.ubooku.payload.auth.RegisterRequest;
import com.agh.iet.ubooku.repository.UserRepository;
import com.agh.iet.ubooku.security.token.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

/**
 * These methods should probably be extracted to dedicated services
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email already taken"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .id(UUID.randomUUID().toString())
                .roles(new HashSet<>(Collections.singleton(new Role(RoleName.ROLE_USER))))
                .provider(AuthProvider.local)
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        User result = userRepository.save(user);

        logger.info("Created User " + user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully!"));
    }
}
