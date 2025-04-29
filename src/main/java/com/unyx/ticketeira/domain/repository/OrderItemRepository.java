package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    List<OrderItem> findByOrderId(String orderId);
    Optional<OrderItem> findByTicketCode(String ticketCode);

    @Query("SELECT COUNT(oi) FROM OrderItem oi WHERE oi.ticket.id = :ticketId AND oi.order.status = 'CONFIRMED'")
    Long countConfirmedTickets(@Param("ticketId") String ticketId);
}