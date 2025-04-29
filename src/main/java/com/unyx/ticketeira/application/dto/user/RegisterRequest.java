package com.unyx.ticketeira.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Failed email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Document is required")
        @Size(min = 11, max = 11)
        String document,

        @NotBlank(message = "Phone is required")
        String phone
) {
}
