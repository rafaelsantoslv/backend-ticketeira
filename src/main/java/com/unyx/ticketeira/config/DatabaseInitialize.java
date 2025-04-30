package com.unyx.ticketeira.config;

import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialize {
    private final RoleRepository roleRepository;

    public DatabaseInitialize(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init(){
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("PRODUCER");
        createRoleIfNotExists("USER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
}
