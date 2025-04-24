package com.unyx.ticketeira.domain.service;

import com.unyx.ticketeira.application.dto.Auth.AuthResponse;
import com.unyx.ticketeira.application.dto.Auth.UserDetails;
import com.unyx.ticketeira.config.JwtUtil;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.UserRepository;
import com.unyx.ticketeira.exception.InvalidCredentialsException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + "not found"));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        UserDetails userResponse = new UserDetails(user.getEmail(), user.getRole().name());



        return new AuthResponse(token, userResponse);
    }


    public User register(User email) {
        if(userRepository.findByEmail(email.getEmail()).isPresent()){
            throw new UserNotFoundException("Email already exists");
        }
        email.setRole(Role.USER);

        email.setPassword(passwordEncoder.encode(email.getPassword()));

        return userRepository.save(email);
    }
}
