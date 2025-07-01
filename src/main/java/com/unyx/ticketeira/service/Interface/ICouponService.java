package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.model.Coupon;

import java.util.List;

public interface ICouponService {
    Coupon validateAndGetCoupon(String couponId);
    void markCouponAsUsed(Coupon coupon);
    List<Coupon> getCouponsByEventId(String eventId);
}
