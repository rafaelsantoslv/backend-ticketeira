package com.unyx.ticketeira.service;

import com.unyx.ticketeira.config.security.JwtUtil;
import com.unyx.ticketeira.dto.user.LoginRequest;
import com.unyx.ticketeira.dto.user.LoginResponse;
import com.unyx.ticketeira.dto.user.RegisterRequest;
import com.unyx.ticketeira.dto.user.RegisterResponse;
import com.unyx.ticketeira.exception.InvalidCredentialsException;
import com.unyx.ticketeira.exception.RoleNotFoundException;
import com.unyx.ticketeira.exception.UnauthorizedException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import com.unyx.ticketeira.model.Role;
import com.unyx.ticketeira.model.User;
import com.unyx.ticketeira.repository.RoleRepository;
import com.unyx.ticketeira.repository.UserRepository;
import com.unyx.ticketeira.service.Interface.IAuthService;
import com.unyx.ticketeira.service.Interface.IUserService;
import com.unyx.ticketeira.util.ConvertDTO;
import com.unyx.ticketeira.util.PasswordUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest user, HttpServletResponse response) {
        User userExists = userRepository.findByEmail(user.email()).orElseThrow(() -> new InvalidCredentialsException("User or password is incorrectT"));
        if(!PasswordUtil.matches(user.password(), userExists.getPassword())){
            System.out.println(user.password() + " + " + userExists.getPassword());
            throw new UnauthorizedException("User or password is incorrect");
        }

        String token = jwtUtil.generateToken(
                userExists.getId(),
                userExists.getEmail(),
                userExists.getRole().getName(),
                userExists.getName()
        );

        createAndSetCookie(response, token);

        return new LoginResponse(
                userExists.getEmail(),
                userExists.getName(),
                userExists.getRole().getName()
        );
    }

    public RegisterResponse register(RegisterRequest user){
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role already exists"));

        if(userService.existsByEmail(user.email())){
            throw new InvalidCredentialsException("Email already exists");
        }
        if(userService.existsByDocument(user.document())){
            throw new UserNotFoundException("Document already exists");
        }

        User convertUser = ConvertDTO.convertUser(user, userRole);

        User registerUser = userRepository.save(convertUser);

        return new RegisterResponse(registerUser.getId(), "Success created User");

    }


    private void createAndSetCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie("token", value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

}
