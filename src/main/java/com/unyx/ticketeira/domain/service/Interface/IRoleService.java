package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Role create(Role role);
    Role update(String id, Role role);
    void delete(String id);
    Optional<Role> findById(String id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
}
