package com.unyx.ticketeira.application.controller;


import com.unyx.ticketeira.application.dto.user.LoginRequest;
import com.unyx.ticketeira.application.dto.user.LoginResponse;
import com.unyx.ticketeira.application.dto.user.RegisterResponse;
import com.unyx.ticketeira.application.dto.user.RegisterRequest;
import com.unyx.ticketeira.application.usecases.auth.LoginUserUseCase;
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
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {

        RegisterResponse response = registerUserUseCase.execute(request);


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = loginUserUseCase.execute(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
