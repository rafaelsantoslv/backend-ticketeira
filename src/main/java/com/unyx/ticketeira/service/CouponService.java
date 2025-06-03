package com.unyx.ticketeira.service;

import com.unyx.ticketeira.exception.CouponNotFoundException;
import com.unyx.ticketeira.model.Coupon;
import com.unyx.ticketeira.repository.CouponRepository;
import com.unyx.ticketeira.service.Interface.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.unyx.ticketeira.constant.SystemMessages.COUPON_ACCESS_DENIED;
import static com.unyx.ticketeira.constant.SystemMessages.COUPON_NOT_FOUND;

@Service
public class CouponService implements ICouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Coupon validateAndGetCoupon(String couponId) {
        if (couponId == null || couponId.isBlank()) {
            return null;
        }
        Coupon coupon = couponRepository.findByCode(couponId)
                .orElseThrow(() -> new CouponNotFoundException(COUPON_NOT_FOUND));
        if (coupon.getUsageCount() >= coupon.getUsageLimit()) {
            throw new CouponNotFoundException(COUPON_ACCESS_DENIED);
        }
        return coupon;
    }
}
