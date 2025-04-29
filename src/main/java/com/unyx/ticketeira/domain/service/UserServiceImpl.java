package com.unyx.ticketeira.domain.service;

import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.application.dto.User.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.UserRepository;
import com.unyx.ticketeira.domain.service.Interface.IUserService;
import com.unyx.ticketeira.domain.util.ConvertDTO;
import com.unyx.ticketeira.exception.RoleNotFoundException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(CreateUserDTO user, Role userRole){
        User userCreate = ConvertDTO.convertUser(user, userRole);

        return userRepository.save(userCreate);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Id already exists"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existsByDocument(String document) {
        return userRepository.findByDocument(document).isPresent();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Email already exists"));
    }

    public User findByDocument(String document) {
        return userRepository.findByDocument(document).orElseThrow(() -> new RoleNotFoundException("Document already not exists"));
    }

    public User update(String id, UpdateUserDTO user){
        User userExists = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Id already not exists"));

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
