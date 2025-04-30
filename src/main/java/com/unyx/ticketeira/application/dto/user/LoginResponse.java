package com.unyx.ticketeira.application.dto.user;

public record LoginResponse(
        String email,
        String name,
        String role,
        String token
) {
}
