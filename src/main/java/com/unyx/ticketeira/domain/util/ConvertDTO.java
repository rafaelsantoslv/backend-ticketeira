package com.unyx.ticketeira.domain.util;

import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.application.dto.User.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;

public class ConvertDTO {

    public static User convertUser(CreateUserDTO dto, Role userRole) {
        User user = new User();


        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setName(dto.name());
        user.setDocument(dto.document());
        user.setPhone(dto.phone());
        user.setRole(userRole);

        return user;
    }

    public static User convertUser(User userUpdate, UpdateUserDTO dto) {


        userUpdate.setEmail(dto.email());
        userUpdate.setName(dto.name());
        userUpdate.setDocument(dto.document());
        userUpdate.setPhone(dto.phone());

        return userUpdate;

    }
}
