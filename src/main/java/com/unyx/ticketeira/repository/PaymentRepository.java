package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Payment;
import com.unyx.ticketeira.repository.projection.PaymentMethodSummaryProjection;
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

    @Query(value = """
    SELECT
        m.method AS payment_method,
        COALESCE(COUNT(p.id), 0) AS total_payments,
        COALESCE(SUM(p.amount), 0) AS total_value,
        COALESCE(COUNT(o.id), 0) AS total_sold
      FROM (
        SELECT 'credit_card' AS method
        UNION ALL
        SELECT 'pix'
        UNION ALL
        SELECT 'boleto'
      ) m
      LEFT JOIN payments p ON p.method = m.method
      LEFT JOIN orders o ON p.order_id = o.id AND o.event_id = :eventId
        AND p.status = 'PAID'
      GROUP BY m.method
      ORDER BY m.method;
""", nativeQuery = true)
    List<PaymentMethodSummaryProjection> getPaymentSummaryByMethod(@Param("eventId") String eventId);

    @Query("SELECT p FROM Payment p WHERE p.order.eventId = :eventId")
    List<Payment> findByEventId(@Param("eventId") String eventId);
}
