package com.unyx.ticketeira.util;

import com.unyx.ticketeira.config.security.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static AuthenticatedUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (AuthenticatedUser) auth.getPrincipal();
    }
}
