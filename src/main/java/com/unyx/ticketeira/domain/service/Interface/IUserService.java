package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.application.dto.User.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;

import java.util.List;

public interface IUserService {
    User create(CreateUserDTO user, Role userRole);
    User update(String id, UpdateUserDTO userData);
    void delete(String id);

    User findById(String id);
    User findByEmail(String email);
    User findByDocument(String document);
    List<User> findAll();

    boolean existsByEmail(String email);
    boolean existsByDocument(String document);


}
