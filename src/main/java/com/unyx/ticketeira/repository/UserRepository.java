package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByDocument(String document);
    boolean existsByEmail(String email);
    List<User> findByIsActive(Boolean isActive);

    @Query("SELECT u FROM User u WHERE u.emailVerified = false AND u.createdAt < :date")
    List<User> findUnverifiedUsers(@Param("date") LocalDateTime date);
}
