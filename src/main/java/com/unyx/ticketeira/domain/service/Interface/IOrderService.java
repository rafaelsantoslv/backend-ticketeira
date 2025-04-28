package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Order create(Order order);
    Order update(String id, Order order);
    void delete(String id);
    Optional<Order> findById(String id);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUser(String userId);
    List<Order> findByEvent(String eventId);
    Order updateStatus(String id, String status);
    Order applyCoupon(String id, String couponCode);
    BigDecimal calculateTotal(String id);
    void expire(String id);
    List<Order> findPending();
    List<Order> findExpired();
    boolean validateOrder(String id);
}
