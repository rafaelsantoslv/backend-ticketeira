package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.user.UpdateUserDTO;
import com.unyx.ticketeira.model.User;

import java.util.List;

public interface IUserService {

    boolean existsByEmail(String email);
    boolean existsByDocument(String document);

    User validateUserAndGetUser(String userId);


}
