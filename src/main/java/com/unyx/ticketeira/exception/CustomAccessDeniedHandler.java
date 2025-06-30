package com.unyx.ticketeira.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unyx.ticketeira.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException {
        ApiErrorResponse apiError = new ApiErrorResponse(
                HttpServletResponse.SC_FORBIDDEN,
                "Forbidden",
                "Acesso negado: você não tem permissão para acessar este recurso.",
                request.getRequestURI()
        );
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}