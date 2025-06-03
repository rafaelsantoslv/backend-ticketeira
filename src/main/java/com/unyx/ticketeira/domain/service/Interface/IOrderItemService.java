package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface IOrderItemService {
    OrderItem create(OrderItem orderItem);
    OrderItem update(String id, OrderItem orderItem);
    void delete(String id);
    Optional<OrderItem> findById(String id);
    List<OrderItem> findByOrder(String orderId);
    String generateTicketCode(String orderItemId);
    String generateQRCode(String orderItemId);
    boolean validateTicket(String code);
    OrderItem cancelTicket(String id);
    void resendTicket(String id);
    void updateAttendeeInfo(String id, String name, String email, String document);
}
