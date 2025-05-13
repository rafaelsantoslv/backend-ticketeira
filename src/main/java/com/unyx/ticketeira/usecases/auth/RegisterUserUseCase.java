package com.unyx.ticketeira.usecases.auth;

import com.unyx.ticketeira.dto.user.RegisterResponse;
import com.unyx.ticketeira.dto.user.RegisterRequest;
import com.unyx.ticketeira.model.Role;
import com.unyx.ticketeira.model.User;
import com.unyx.ticketeira.repository.RoleRepository;
import com.unyx.ticketeira.repository.UserRepository;
import com.unyx.ticketeira.service.Interface.IUserService;
import com.unyx.ticketeira.util.ConvertDTO;
import com.unyx.ticketeira.exception.InvalidCredentialsException;
import com.unyx.ticketeira.exception.RoleNotFoundException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final IUserService userService;



    public RegisterUserUseCase(RoleRepository roleRepository, IUserService userService, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public RegisterResponse execute(RegisterRequest user){
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

}
