package com.unyx.ticketeira.dto.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponDTO(
        String id,
        String code,
        BigDecimal discountValue,
        boolean active,
        String discountType,
        long usageCount,
        long usageLimit,
        LocalDateTime createdAt
) {
}
