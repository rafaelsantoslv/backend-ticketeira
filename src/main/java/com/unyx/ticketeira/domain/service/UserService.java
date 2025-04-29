package com.unyx.ticketeira.domain.service;

import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.application.dto.User.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.UserRepository;
import com.unyx.ticketeira.domain.service.Interface.IUserService;
import com.unyx.ticketeira.domain.util.ConvertDTO;
import com.unyx.ticketeira.domain.util.PasswordUtil;
import com.unyx.ticketeira.exception.InvalidCredentialsException;
import com.unyx.ticketeira.exception.RoleNotFoundException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(CreateUserDTO user, Role userRole){
        User userCreate = ConvertDTO.convertUser(user, userRole);

        return userRepository.save(userCreate);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Id already exists"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Email already exists"));
    }

    public User update(String id, UpdateUserDTO user){
        User userExists = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Id already exists"));

        User userConvert = ConvertDTO.convertUser(userExists, user);

        return userRepository.save(userConvert);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(String id) {
        User userExists = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Id already exists"));
        userRepository.delete(userExists);
    }

}
