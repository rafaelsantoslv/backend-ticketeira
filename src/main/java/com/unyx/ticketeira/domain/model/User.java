package com.unyx.ticketeira.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(of="id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String phone;

    @Column(length = 20)
    private String document;

    @Column(name = "profile_image")
    private String profileImage;


    @Column(nullable = false, name = "is_active")
    private Boolean isActive = true;

    @Column(nullable = false, name = "email_verified")
    private Boolean emailVerified = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Retorna a role do usuário como uma lista de GrantedAuthority
//        return Collections.singleton(() -> "ROLE_" + role.name());
//    }
//
//    @Override
//    public String getUsername() {
//        return email; // O email será usado como o "username" no Spring Security
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // Pode ser ajustado conforme a lógica de expiração de conta
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // Pode ser ajustado conforme a lógica de bloqueio de conta
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // Pode ser ajustado conforme a lógica de expiração de credenciais
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true; // Pode ser ajustado conforme a lógica de ativação de conta
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
}
