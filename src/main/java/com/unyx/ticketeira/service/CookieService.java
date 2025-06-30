package com.unyx.ticketeira.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    @Value("${app.cookie.secure:false}") // Configura no application.properties
    private boolean isSecure;

    @Value("${app.cookie.domain:localhost}") // Configura no application.properties
    private String domain;


    public void createAndSetCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite(isSecure? "None" : "Lax")
                .domain(domain)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

    }

    public void invalidateAuthCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(0)
                .sameSite(isSecure ? "None" : "Lax")
                .domain(domain)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }



}
