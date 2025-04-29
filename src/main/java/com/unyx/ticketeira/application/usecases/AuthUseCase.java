package com.unyx.ticketeira.application.usecases;

import com.unyx.ticketeira.application.dto.Auth.RegisterResponse;
import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.RoleRepository;
import com.unyx.ticketeira.domain.service.Interface.IUserService;
import com.unyx.ticketeira.exception.InvalidCredentialsException;
import com.unyx.ticketeira.exception.RoleNotFoundException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase {

    private final RoleRepository roleRepository;
    private final IUserService userService;



    public AuthUseCase(RoleRepository roleRepository, IUserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    public RegisterResponse register(CreateUserDTO user){
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role already exists"));

        if(userService.existsByEmail(user.email())){
            throw new InvalidCredentialsException("Email already exists");
        }
        if(userService.existsByDocument(user.document())){
            throw new UserNotFoundException("Document already exists");
        }


        User registerUser = userService.create(user, userRole);

        return new RegisterResponse(registerUser.getId(), "Success created User");

    }

}
