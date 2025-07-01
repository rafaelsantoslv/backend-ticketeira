//package com.unyx.ticketeira.controller;
//
//import com.unyx.ticketeira.config.security.AuthenticatedUser;
//import com.unyx.ticketeira.dto.order.OrderRequest;
//import com.unyx.ticketeira.dto.order.OrderResponse;
//import com.unyx.ticketeira.service.Interface.IOrderService;
//import com.unyx.ticketeira.util.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/order")
//public class OrderController {
//
//    @Autowired
//    private IOrderService orderService;
//
//    @PostMapping
//    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
//        AuthenticatedUser user = SecurityUtils.getCurrentUser();
//
//        return ResponseEntity.ok(orderService.createOrder(user.getId(), request));
//
//    }
//}
