package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.model.Coupon;

public interface ICouponService {
    Coupon validateAndGetCoupon(String couponId);
    void markCouponAsUsed(Coupon coupon);
}
