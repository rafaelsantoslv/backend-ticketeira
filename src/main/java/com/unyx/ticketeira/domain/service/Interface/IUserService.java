package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.application.dto.User.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;

import java.util.List;

public interface IUserService {
    User create(CreateUserDTO user, Role userRole);
    User findById(String id);
    User findByEmail(String email);
    User update(String id, UpdateUserDTO userData);
    List<User> findAll();
    void delete(String id);
//    void assignRole(String userId, String roleId);
//    void requestPasswordReset(String email);
//    void resetPassword(String token, String newPassword);
//    void verifyEmail(String token);
}
