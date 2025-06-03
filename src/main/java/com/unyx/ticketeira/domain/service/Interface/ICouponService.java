package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.Coupon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICouponService {
    Coupon create(Coupon coupon);
    Coupon update(String id, Coupon coupon);
    void delete(String id);
    Optional<Coupon> findById(String id);
    Optional<Coupon> findByCode(String code);
    List<Coupon> findByEvent(String eventId);
    BigDecimal calculateDiscount(String couponId, BigDecimal amount);
    void incrementUsage(String id);
    boolean isValid(String code);
}
