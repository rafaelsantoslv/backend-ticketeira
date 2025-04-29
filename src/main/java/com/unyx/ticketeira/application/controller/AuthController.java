package com.unyx.ticketeira.application.controller;


import com.unyx.ticketeira.application.dto.Auth.RegisterRequest;
import com.unyx.ticketeira.application.dto.Auth.RegisterResponse;
import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.useCases.AuthUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid CreateUserDTO request) {

        RegisterResponse response = authUseCase.register(request);


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
