package com.unyx.ticketeira.usecases.auth;

import com.unyx.ticketeira.dto.user.LoginRequest;
import com.unyx.ticketeira.dto.user.LoginResponse;
import com.unyx.ticketeira.config.security.JwtUtil;
import com.unyx.ticketeira.model.User;
import com.unyx.ticketeira.repository.UserRepository;
import com.unyx.ticketeira.util.PasswordUtil;
import com.unyx.ticketeira.exception.InvalidCredentialsException;
import com.unyx.ticketeira.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginUserUseCase(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse execute(LoginRequest user) {
        User userExists = userRepository.findByEmail(user.email()).orElseThrow(() -> new InvalidCredentialsException("User or password is incorrectT"));
        if(!PasswordUtil.matches(user.password(), userExists.getPassword())){
            System.out.println(user.password() + " + " + userExists.getPassword());
            throw new UnauthorizedException("User or password is incorrect");
        }

        String token = jwtUtil.generateToken(userExists.getId(),userExists.getEmail(), userExists.getRole().getName(), userExists.getName());
        return new LoginResponse(userExists.getEmail(), userExists.getName(), userExists.getRole().getName(), token);
    }
}
