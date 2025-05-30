//package com.unyx.ticketeira.service;
//
//import com.unyx.ticketeira.exception.*;
//import com.unyx.ticketeira.model.Batch;
//import com.unyx.ticketeira.model.Coupon;
//import com.unyx.ticketeira.model.Event;
//import com.unyx.ticketeira.model.Sector;
//import com.unyx.ticketeira.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//@Service
//public class PaymentService {
//
//    @Autowired
//    private EventRepository eventRepository;
//
//    @Autowired
//    private SectorRepository sectorRepository;
//
//    @Autowired
//    private BatchRepository batchRepository;
//
//    @Autowired
//    private CouponRepository couponRepository;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    public void createOrder(
//            String eventId,
//            String sectorId,
//            String batchId,
//            int quantity,
//            String coupon
//    ) {
//        Event eventExists = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Evento não encontrado"));
//        if(!eventExists.getIsPublished()) {
//            throw new AccessDeniedException("Evento não disponível para visualização");
//        }
//        Sector sectorExists = sectorRepository.findById(sectorId).orElseThrow(() -> new SectorNotFoundException("Setor não encontrado"));
//
//        Batch batchExists = batchRepository.findById(batchId).orElseThrow(() -> new BatchNotFoundException("Lote não encontrado"));
//        if(!batchExists.getIsActive())  {
//            throw  new AccessDeniedException("Lote não disponível para compra");
//        }
//
//        Optional<Coupon> optionalCoupon = Optional.empty();
//        if (coupon != null && !coupon.isBlank()) {
//            optionalCoupon = couponRepository.findByCode(coupon);
//
//            if (optionalCoupon.isEmpty()) {
//                throw new CouponNotFoundException("Cupom não encontrado");
//            }
//
//            if (optionalCoupon.get().getUsageCount() >= optionalCoupon.get().getUsageLimit()) {
//                throw new CouponNotFoundException("Cupom não está mais disponível");
//            }
//        }
//
//        BigDecimal unitPrice = batchExists.getPrice();
//        BigDecimal quantityBD = BigDecimal.valueOf(quantity);
//        BigDecimal subTotal = unitPrice.multiply(quantityBD);
//
//        BigDecimal discount = optionalCoupon.map(c -> {
//            if (c.getDiscountType().equals("PERCENT")) {
//                // desconto percentual: subTotal * (percent / 100)
//                return subTotal.multiply(c.getDiscountValue().divide(BigDecimal.valueOf(100)));
//            } else {
//                // desconto fixo: R$ X
//                return c.getDiscountValue();
//            }
//        }).orElse(BigDecimal.ZERO);
//
//        BigDecimal total = subTotal.subtract(discount);
//
//        BigDecimal fee = total.multiply(BigDecimal.valueOf(0.10));
//        BigDecimal finalAmount = total.add(fee);
//
//        user_id,
//        event_id,
//        coupon_id,
//
//
//    }
//}
