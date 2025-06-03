package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.user.LoginRequest;
import com.unyx.ticketeira.dto.user.LoginResponse;
import com.unyx.ticketeira.dto.user.RegisterRequest;
import com.unyx.ticketeira.dto.user.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest user, HttpServletResponse response);
    RegisterResponse register(RegisterRequest user);
}
