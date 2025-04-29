package com.unyx.ticketeira;

import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

@SpringBootTest
public class AuthServiceTest {

    @Test
    void sholdCreateUserSucessfully(){
        CreateUserDTO dto = new CreateUserDTO("joao@example.com", "123321111", "Rafael dos Santos", "11122233321", "48988228714");
        User user = new User();
        Role role = new Role();
        role.setId(UUID.randomUUID().toString());
        role.setName("USER");
        when(roleRepository.findByName("USER")).thenReturn(userRole);

        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setName(dto.name());
        user.setDocument(dto.document());
        user.setPhone(dto.phone());
        user.setPhone(dto.phone());
        user.setRole();
    }
}
