package com.unyx.ticketeira.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromHeader(request);

        if (token != null) {
            try {
                // Validar o token
                if (jwtUtil.validateToken(token)) {
                    String userId = jwtUtil.extractUserId(token);
                    String email = jwtUtil.extractEmail(token);
                    String role = jwtUtil.extractRole(token);

                    // Criar o usuário autenticado
                    AuthenticatedUser authenticatedUser = new AuthenticatedUser(userId, email, role);

                    // Criar a autenticação
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());

                    // Definir no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Token inválido, retornar 401 Unauthorized
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido");
                    return;
                }
            } catch (ExpiredJwtException e) {
                // Token expirado, retornar 401 Unauthorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expirado");
                return;
            } catch (Exception e) {
                // Outro erro durante a validação do token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Erro ao validar token");
                return;
            }
        }

        // Seguir com o filtro caso o token seja válido
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove o prefixo "Bearer "
        }
        return null;
    }
}
