//package com.unyx.ticketeira.domain.service;
//
//import com.unyx.ticketeira.application.dto.Auth.AuthResponse;
//import com.unyx.ticketeira.application.dto.Auth.UserDetails;
//import com.unyx.ticketeira.config.JwtUtil;
//import com.unyx.ticketeira.domain.model.User;
//import com.unyx.ticketeira.domain.repository.UserRepository;
//import com.unyx.ticketeira.exception.InvalidCredentialsException;
//import com.unyx.ticketeira.exception.UserNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//
//    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//    public AuthResponse login(String email, String password) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User with email " + email + "not found"));
//
//        if(!passwordEncoder.matches(password, user.getPassword())) {
//            throw new InvalidCredentialsException("Invalid email or password");
//        }
//        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getName());
//        UserDetails userResponse = new UserDetails(user.getEmail(), user.getRole().getName());
//
//
//
//        return new AuthResponse(token, userResponse);
//    }
//
//
//    public User register(User user) {
//        if(userRepository.findByEmail(user.getEmail()).isPresent()){
//            throw new UserNotFoundException("Email already exists");
//        }
//        user.setRole(Roles.USER);
//
//        user.
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        return userRepository.save(user);
//    }
//}
