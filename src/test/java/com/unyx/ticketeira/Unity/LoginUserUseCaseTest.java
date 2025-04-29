package com.unyx.ticketeira.Unity;
import com.unyx.ticketeira.application.dto.user.LoginRequest;
import com.unyx.ticketeira.application.dto.user.LoginResponse;
import com.unyx.ticketeira.application.usecases.auth.LoginUserUseCase;
import com.unyx.ticketeira.config.JwtUtil;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.UserRepository;
import com.unyx.ticketeira.domain.util.PasswordUtil;
import com.unyx.ticketeira.exception.InvalidCredentialsException;
import com.unyx.ticketeira.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    @Test
    @DisplayName("Deve realizar login com sucesso quando email e senha estiverem corretos")
    void shouldLoginSuccessfully() {
        // Arrange
        String email = "user@example.com";
        String password = "password123";
        String hashedPassword = PasswordUtil.encryptPassword(password);
        LocalDateTime now = LocalDateTime.now();
        Role role = new Role("73299201-cb85-4d7b-aca2-d351da9e6010", "user", LocalDateTime.now(), LocalDateTime.now());
        User user = new User(
                "33439201-cb85-4d7b-ad22-d351da9e6010",
                "User Test",
                email,
                hashedPassword,
                "123",
                "999",
                null,           // profileImage
                true,           // isActive
                true,           // emailVerified
                role,
                now,
                now
        );
        String token = "jwt.token";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(email, "user", "User Test")).thenReturn(token);

        LoginRequest request = new LoginRequest(email, password);

        // Act
        LoginResponse response = loginUserUseCase.execute(request);

        // Assert
        assertEquals(email, response.email());
        assertEquals("User Test", response.name());
        assertEquals("user", response.role());
        assertEquals(token, response.token());
    }

    @Test
    @DisplayName("Deve lançar InvalidCredentialsException quando o e-mail não for encontrado")
    void shouldThrowInvalidCredentialsWhenEmailNotFound() {
        // Arrange
        LoginRequest request = new LoginRequest("naoexiste@email.com", "qualquer");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUserUseCase.execute(request));
    }

    @Test
    @DisplayName("Deve lançar UnauthorizedException quando a senha estiver incorreta")
    void shouldThrowUnauthorizedWhenPasswordIncorrect() {
        // Arrange
        String email = "user@example.com";
        LocalDateTime now = LocalDateTime.now();
        String correctPassword = PasswordUtil.encryptPassword("senhaCorreta");
        Role role = new Role("73299201-cb85-4d7b-aca2-d351da9e6010", "user", LocalDateTime.now(), LocalDateTime.now());
        User user = new User(
                "33439201-cb85-4d7b-ad22-d351da9e6010",
                "User Test",
                email,
                correctPassword,
                "123",
                "999",
                null,           // profileImage
                true,           // isActive
                true,           // emailVerified
                role,
                now,
                now
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        LoginRequest request = new LoginRequest(email, "senhaErrada");

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> loginUserUseCase.execute(request));
    }
}
