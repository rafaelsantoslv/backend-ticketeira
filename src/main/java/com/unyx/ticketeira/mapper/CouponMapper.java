package com.unyx.ticketeira.mapper;

import com.unyx.ticketeira.dto.coupon.CouponDTO;
import com.unyx.ticketeira.model.Coupon;

public class CouponMapper {
    public static CouponDTO toDTO(Coupon coupon) {
        return new CouponDTO(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscountValue(),
                coupon.isActive(),
                coupon.getDiscountType(),
                coupon.getUsageCount(),
                coupon.getUsageLimit(),
                coupon.getCreatedAt()
        );
    }}
}
