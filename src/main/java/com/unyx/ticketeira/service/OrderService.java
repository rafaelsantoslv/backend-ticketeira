package com.unyx.ticketeira.service;

import com.unyx.ticketeira.exception.*;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


    @Service
    public class OrderService {
        @Autowired
        private EventRepository eventRepository;

        @Autowired
        private SectorRepository sectorRepository;

        @Autowired
        private BatchRepository batchRepository;

        @Autowired
        private CouponRepository couponRepository;

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private OrderItemRepository orderItemRepository;

        @Transactional
        public Order createOrder(
                User user,
                String eventId,
                List<OrderItemRequest> items,
                String couponCode
        ) {
            Event event = validateEvent(eventId);
            Coupon coupon = validateAndGetCoupon(couponCode);

            // Monta os itens do pedido e valida setor/lote
            List<OrderItem> orderItems = buildOrderItems(items);

            // Calcula o total antes do desconto
            BigDecimal totalBeforeDiscount = calculateTotal(orderItems);

            // Calcula desconto
            BigDecimal discount = calculateDiscount(totalBeforeDiscount, coupon);

            BigDecimal totalAfterDiscount = totalBeforeDiscount.subtract(discount);

            // Taxa da plataforma (10%)
            BigDecimal fee = totalAfterDiscount.multiply(BigDecimal.valueOf(0.10));

            BigDecimal finalAmount = totalAfterDiscount.add(fee);

            // Cria e salva o pedido
            Order order = new Order();
            order.setStatus("Valido");
            order.setUser(user);
            order.setDiscount(discount);
            order.setFees(fee);
            order.setTotal(finalAmount);
            order.setCoupon(coupon);

            orderRepository.save(order);

            // Associa os itens ao pedido e salva
            for (OrderItem item : orderItems) {
                item.setOrder(order);
            }
            orderItemRepository.saveAll(orderItems);

            // Atualiza uso do cupom, se houver
            if (coupon != null) {
                coupon.setUsageCount(coupon.getUsageCount() + 1);
                couponRepository.save(coupon);
            }

            return order;
        }

        private Event validateEvent(String eventId) {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EventNotFoundException("Evento não encontrado"));
            if (!event.getIsPublished()) {
                throw new AccessDeniedException("Evento não disponível para visualização");
            }
            return event;
        }

        private Coupon validateAndGetCoupon(String couponCode) {
            if (couponCode == null || couponCode.isBlank()) {
                return null;
            }
            Coupon coupon = couponRepository.findByCode(couponCode)
                    .orElseThrow(() -> new CouponNotFoundException("Cupom não encontrado"));
            if (coupon.getUsageCount() >= coupon.getUsageLimit()) {
                throw new CouponNotFoundException("Cupom não está mais disponível");
            }
            return coupon;
        }

        private List<OrderItem> buildOrderItems(List<OrderItemRequest> items) {
            return items.stream().map(item -> {
                Sector sector = sectorRepository.findById(item.getSectorId())
                        .orElseThrow(() -> new SectorNotFoundException("Setor não encontrado: " + item.getSectorId()));
                Batch batch = batchRepository.findById(item.getBatchId())
                        .orElseThrow(() -> new BatchNotFoundException("Lote não encontrado: " + item.getBatchId()));

                return getOrderItem(item, batch, sector);
            }).toList();
        }

        private static OrderItem getOrderItem(OrderItemRequest item, Batch batch, Sector sector) {
            if (!batch.getIsActive()) {
                throw new AccessDeniedException("Lote inativo: " + item.getBatchId());
            }
            BigDecimal unitPrice = batch.getPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setSector(sector);
            orderItem.setBatch(batch);
            orderItem.setUnitPrice(unitPrice);
            orderItem.setQuantity(item.getQuantity());
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