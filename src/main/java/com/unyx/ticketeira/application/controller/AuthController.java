//package com.unyx.ticketeira.application.controller;
//
//import com.unyx.ticketeira.application.dto.Auth.AuthRequest;
//import com.unyx.ticketeira.application.dto.Auth.AuthResponse;
//import com.unyx.ticketeira.application.dto.Auth.RegisterRequest;
//import com.unyx.ticketeira.application.dto.Auth.RegisterResponse;
//import com.unyx.ticketeira.domain.service.AuthService;
//import com.unyx.ticketeira.domain.model.User;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request){
//        AuthResponse response = authService.login(request.getEmail(), request.getPassword());
//        return ResponseEntity.ok(response);
//    }
//
//
//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
//        User user = new User();
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
//
//
//        User registeredUser = authService.register(user);
//
//        RegisterResponse response = new RegisterResponse(
//                registeredUser.getId(),
//                registeredUser.getEmail(),
//                registeredUser.getRole().getName()
//        );
//        return ResponseEntity.ok(response);
//    }
//}
