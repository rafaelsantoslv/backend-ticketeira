package com.unyx.ticketeira.controller;


import com.unyx.ticketeira.dto.user.LoginRequest;
import com.unyx.ticketeira.dto.user.LoginResponse;
import com.unyx.ticketeira.dto.user.RegisterResponse;
import com.unyx.ticketeira.dto.user.RegisterRequest;
import com.unyx.ticketeira.service.CookieService;
import com.unyx.ticketeira.service.Interface.IAuthService;
import com.unyx.ticketeira.usecases.auth.LoginUserUseCase;
import com.unyx.ticketeira.usecases.auth.RegisterUserUseCase;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpServletResponse responseCookie) {
        LoginResponse response = authService.login(request, responseCookie);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {

        RegisterResponse response = authService.register(request);


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        cookieService.invalidateAuthCookie(response);

        return ResponseEntity.ok().body("");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser)) {
            throw new UnauthorizedException("Token inválido");
        }
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        Map<String, String> response = Map.of(
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole()
        );
        return ResponseEntity.ok(response);
    }
}
