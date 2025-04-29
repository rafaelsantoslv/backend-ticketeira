package com.unyx.ticketeira.application.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private String email;
    private String role;
}
