package com.unyx.ticketeira.dto.user;

public record LoginResponse(
        String email,
        String name,
        String role
) {
}
