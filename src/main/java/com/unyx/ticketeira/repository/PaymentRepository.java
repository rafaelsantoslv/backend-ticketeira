package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByOrderId(String orderId);
    Optional<Payment> findByProviderPaymentId(String providerPaymentId);
    List<Payment> findByStatus(String status);

    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.createdAt < :expirationDate")
    List<Payment> findExpiredPayments(@Param("expirationDate") LocalDateTime expirationDate);
}
