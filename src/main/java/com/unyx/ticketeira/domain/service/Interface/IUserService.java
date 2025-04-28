package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.application.dto.User.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.User;

import java.util.List;

public interface IUserService {
    User create(CreateUserDTO userData);
    User findById(String id);
    User findByEmail(String email);
    User update(String id, UpdateUserDTO userData);
    List<User> findAll();
    void delete(String id);
    void verifyEmail(String token);
    void resetPassword(String token, String newPassword);
    void requestPasswordReset(String email);
    void assignRole(String userId, String roleId);
}
