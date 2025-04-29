package com.unyx.ticketeira.domain.useCases;

import com.unyx.ticketeira.application.dto.Auth.RegisterResponse;
import com.unyx.ticketeira.application.dto.User.CreateUserDTO;
import com.unyx.ticketeira.application.usecases.AuthUseCase;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.RoleRepository;
import com.unyx.ticketeira.domain.repository.UserRepository;
import com.unyx.ticketeira.domain.service.UserServiceImpl;
import com.unyx.ticketeira.exception.RoleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Tests for create and register clients")
@SpringBootTest
public class AuthUseCaseTest {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserServiceImpl userServiceImpl;
    private AuthUseCase authUseCase;

    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        userRepository = mock(UserRepository.class);
        userServiceImpl = new UserServiceImpl(userRepository);
        authUseCase = new AuthUseCase(roleRepository, userServiceImpl);
    }

    @Test
    @DisplayName("Create User")
    void shouldCreateUserSuccessfully() {
        // Arrange: montar entrada
        CreateUserDTO userDTO = new CreateUserDTO(
                "novousuario@exemplo.com",
                "senha123",
                "rafael dos santos",
                "11111122233",
                "48988228714"
        );

        // Mock da Role
        Role userRole = new Role();
        userRole.setId(UUID.randomUUID().toString());
        userRole.setName("USER");

        // Mock do User que seria salvo no banco
        User savedUser = new User();
        String generatedId = UUID.randomUUID().toString();
        savedUser.setId(generatedId);
        savedUser.setEmail(userDTO.email());
        savedUser.setPassword(userDTO.password());
        savedUser.setName(userDTO.name());
        savedUser.setDocument(userDTO.document());
        savedUser.setPhone(userDTO.phone());
        savedUser.setRole(userRole);

        // Configurando os mocks
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act: executar o método real
        RegisterResponse response = authUseCase.register(userDTO);

        // Assert: validar a saída
        assertNotNull(response);
        assertEquals(generatedId, response.getId());
        assertEquals("Success created User", response.getMessage());

        // Verifica se os métodos foram chamados corretamente
        verify(roleRepository).findByName("USER");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Create User no exists Role ")
    void shouldThrowRoleNotFoundExceptionIfRoleDoesNotExist() {
        // Arrange
        CreateUserDTO userDTO = new CreateUserDTO(
                "novousuario@exemplo.com",
                "password123",
                "rafael dos santos",
                "11111122233",
                "48988228714"
        );

        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RoleNotFoundException.class, () -> authUseCase.register(userDTO));
    }
}
