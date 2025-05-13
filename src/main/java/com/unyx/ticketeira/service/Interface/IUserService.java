package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.user.UpdateUserDTO;
import com.unyx.ticketeira.model.User;

import java.util.List;

public interface IUserService {
    User create(User user);
    User update(String id, UpdateUserDTO userData);
    void delete(String id);

    User findById(String id);
    User findByEmail(String email);
    User findByDocument(String document);
    List<User> findAll();

    boolean existsByEmail(String email);
    boolean existsByDocument(String document);


}
