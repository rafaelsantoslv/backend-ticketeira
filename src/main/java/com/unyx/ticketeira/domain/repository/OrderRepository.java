package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUserId(String userId);
    List<Order> findByEventId(String eventId);
    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.status = 'PENDING' AND o.expiresAt < :now")
    List<Order> findExpiredOrders(@Param("now") LocalDateTime now);

    @Query("SELECT o FROM Order o WHERE o.event.id = :eventId AND o.status = :status AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByEventAndStatusAndDateRange(
            @Param("eventId") String eventId,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}