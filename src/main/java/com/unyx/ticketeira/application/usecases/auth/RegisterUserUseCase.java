package com.unyx.ticketeira.application.usecases.auth;

import com.unyx.ticketeira.application.dto.auth.RegisterResponse;
import com.unyx.ticketeira.application.dto.User.RegisterUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.RoleRepository;
import com.unyx.ticketeira.domain.repository.UserRepository;
import com.unyx.ticketeira.domain.service.Interface.IUserService;
import com.unyx.ticketeira.domain.util.ConvertDTO;
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

    public RegisterResponse execute(RegisterUserDTO user){
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
