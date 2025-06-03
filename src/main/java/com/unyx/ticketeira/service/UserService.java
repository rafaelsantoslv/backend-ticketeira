package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.user.UpdateUserDTO;
import com.unyx.ticketeira.model.User;
import com.unyx.ticketeira.repository.UserRepository;
import com.unyx.ticketeira.service.Interface.IUserService;
import com.unyx.ticketeira.util.ConvertDTO;
import com.unyx.ticketeira.exception.RoleNotFoundException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unyx.ticketeira.constant.SystemMessages.USER_NOT_FOUND;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user){
        return userRepository.save(user);
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

    public User validateUserAndGetUser(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(USER_NOT_FOUND)
        );
    }

}
