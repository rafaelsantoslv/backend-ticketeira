package com.unyx.ticketeira.application.controller;


import com.unyx.ticketeira.application.dto.auth.RegisterResponse;
import com.unyx.ticketeira.application.dto.User.RegisterUserDTO;
import com.unyx.ticketeira.application.usecases.auth.RegisterUserUseCase;
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

    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterUserDTO request) {

        RegisterResponse response = registerUserUseCase.execute(request);


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
