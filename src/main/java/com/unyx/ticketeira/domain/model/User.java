package com.unyx.ticketeira.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tab_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna a role do usuário como uma lista de GrantedAuthority
        return Collections.singleton(() -> "ROLE_" + role.name());
    }

    @Override
    public String getUsername() {
        return email; // O email será usado como o "username" no Spring Security
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Pode ser ajustado conforme a lógica de expiração de conta
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode ser ajustado conforme a lógica de bloqueio de conta
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Pode ser ajustado conforme a lógica de expiração de credenciais
    }

    @Override
    public boolean isEnabled() {
        return true; // Pode ser ajustado conforme a lógica de ativação de conta
    }
}
