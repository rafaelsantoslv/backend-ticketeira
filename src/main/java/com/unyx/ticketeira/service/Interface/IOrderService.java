package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.order.OrderRequest;
import com.unyx.ticketeira.dto.order.OrderResponse;
import com.unyx.ticketeira.model.Order;


public interface IOrderService {
//    OrderResponse createOrder(String userId, OrderRequest request);
    Order validateOrderAndGetOrder(String orderId);
}
