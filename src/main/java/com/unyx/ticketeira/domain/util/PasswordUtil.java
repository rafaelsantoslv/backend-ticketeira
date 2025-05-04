package com.unyx.ticketeira.domain.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        System.out.println("Resultado foi: " + passwordEncoder.matches(rawPassword, encodedPassword));
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
