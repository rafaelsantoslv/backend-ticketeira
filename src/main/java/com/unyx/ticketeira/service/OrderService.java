package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.order.OrderItemRequest;
import com.unyx.ticketeira.dto.order.OrderRequest;
import com.unyx.ticketeira.dto.order.OrderResponse;
import com.unyx.ticketeira.exception.*;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.repository.*;
import com.unyx.ticketeira.service.Interface.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.unyx.ticketeira.constant.SystemMessages.*;


@AllArgsConstructor
@Service
    public class OrderService implements IOrderService {

//        private final IEventService eventService;


        private final ICouponService couponService;


        private final ISectorService sectorService;


        private final IUserService userService;


        private final IBatchService batchService;


        private final OrderRepository orderRepository;


        private final OrderItemRepository orderItemRepository;


//        @Transactional
//        public OrderResponse createOrder(
//                String userId,
//                OrderRequest request
//        ) {
//            User userExists = userService.validateUserAndGetUser(userId);
//
//            Event event = eventService.validateAndGetEvent(request.eventId());
//            Coupon coupon = couponService.validateAndGetCoupon(request.coupon());
//
//            // Monta os itens do pedido e valida setor/lote
//            List<OrderItem> orderItems = buildOrderItems(request.items());
//
//            // Calcula o total antes do desconto
//            BigDecimal totalBeforeDiscount = calculateTotal(orderItems);
//
//            // Calcula desconto
//            BigDecimal discount = calculateDiscount(totalBeforeDiscount, coupon);
//
//            BigDecimal totalAfterDiscount = totalBeforeDiscount.subtract(discount);
//
//            // Taxa da plataforma (10%)
//            BigDecimal fee = totalAfterDiscount.multiply(BigDecimal.valueOf(0.10));
//
//            BigDecimal finalAmount = totalAfterDiscount.add(fee);
//
//            // Cria e salva o pedido
//            Order order = new Order();
//            order.setStatus("PENDING");
//            order.setUser(userExists);
//            order.setDiscount(discount);
//            order.setFees(fee);
//            order.setTotal(finalAmount);
//            order.setCoupon(coupon);
//
//            orderRepository.save(order);
//
//            // Associa os itens ao pedido e salva
//            for (OrderItem item : orderItems) {
//                item.setOrder(order);
//            }
//            orderItemRepository.saveAll(orderItems);
//
//            // Atualiza uso do cupom, se houver
//            if (coupon != null) {
//                couponService.markCouponAsUsed(coupon);
//            }
//
//            return OrderResponse.from(
//                    order.getTotal(),
//                    order.getFees(),
//                    order.getId()
//            );
//        }

        public Order validateOrderAndGetOrder(String orderId) {
            Order orderExists = orderRepository.findById(orderId).orElseThrow(
                    () -> new OrderNotFoundException(ORDER_NOT_FOUND)
            );
            if(!orderExists.getStatus().equals("PENDING")) {
                throw new AccessDeniedException(ORDER_ACCESS_DENIED);
            }
            return orderExists;
        }



        private List<OrderItem> buildOrderItems(List<OrderItemRequest> items) {
            return items.stream().map(item -> {
                Sector sector = sectorService.validateSectorAndGetSector(item.sectorId());
                Batch batch = batchService.validateBatchAndGetBatch(item.batchId());
                return getOrderItem(item.quantity(), batch, sector);
            }).toList();
        }

        private static OrderItem getOrderItem(int quantity, Batch batch, Sector sector) {
            if (!batch.getIsActive()) {
                throw new AccessDeniedException( SECTOR_ACCESS_DENIED + " " + batch.getId());
            }
            BigDecimal unitPrice = batch.getPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

            OrderItem orderItem = new OrderItem();
            orderItem.setSector(sector);
            orderItem.setBatch(batch);
            orderItem.setUnitPrice(unitPrice);
            orderItem.setQuantity(quantity);
            orderItem.setSubtotal(subtotal);

            return orderItem;
        }

        private BigDecimal calculateTotal(List<OrderItem> items) {
            return items.stream()
                    .map(OrderItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private BigDecimal calculateDiscount(BigDecimal total, Coupon coupon) {
            if (coupon == null) {
                return BigDecimal.ZERO;
            }
            if ("PERCENT".equalsIgnoreCase(coupon.getDiscountType())) {
                return total.multiply(coupon.getDiscountValue().divide(BigDecimal.valueOf(100)));
            } else {
                return coupon.getDiscountValue();
            }
        }
    }