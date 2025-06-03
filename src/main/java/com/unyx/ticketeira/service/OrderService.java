package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.order.OrderItemRequest;
import com.unyx.ticketeira.dto.order.OrderRequest;
import com.unyx.ticketeira.dto.order.OrderResponse;
import com.unyx.ticketeira.exception.*;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.repository.*;
import com.unyx.ticketeira.service.Interface.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.unyx.ticketeira.constant.SystemMessages.*;


@Service
    public class OrderService implements IOrderService {
        @Autowired
        private IEventService eventService;

        @Autowired
        private ICouponService couponService;

        @Autowired
        private ISectorService sectorService;

        @Autowired
        private IUserService userService;

        @Autowired
        private IBatchService batchService;

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private OrderItemRepository orderItemRepository;


        @Transactional
        public OrderResponse createOrder(
                String userId,
                OrderRequest request
        ) {
            User userExists = userService.validateUserAndGetUser(userId);

            Event event = eventService.validateAndGetEvent(request.eventId());
            Coupon coupon = couponService.validateAndGetCoupon(request.coupon());

            // Monta os itens do pedido e valida setor/lote
            List<OrderItem> orderItems = buildOrderItems(request.items());

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
            order.setUser(userExists);
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
                couponService.markCouponAsUsed(coupon);
            }

            return OrderResponse.from(
                    order.getTotal(),
                    order.getFees(),
                    order.getId()
            );
        }

//        private Event validateEvent(String eventId) {
//            Event event = eventRepository.findById(eventId)
//                    .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
//            if (!event.getIsPublished()) {
//                throw new AccessDeniedException(EVENT_ACCESS_DENIED);
//            }
//            return event;
//        }

//        private Coupon validateAndGetCoupon(String couponCode) {
//            if (couponCode == null || couponCode.isBlank()) {
//                return null;
//            }
//            Coupon coupon = couponRepository.findByCode(couponCode)
//                    .orElseThrow(() -> new CouponNotFoundException(COUPON_NOT_FOUND));
//            if (coupon.getUsageCount() >= coupon.getUsageLimit()) {
//                throw new CouponNotFoundException(COUPON_ACCESS_DENIED);
//            }
//            return coupon;
//        }

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