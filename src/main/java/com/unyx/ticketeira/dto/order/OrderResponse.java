package com.unyx.ticketeira.dto.order;



import java.math.BigDecimal;

public record OrderResponse(

        String price,

        String fees,

        String orderId
) {
    public static OrderResponse from(BigDecimal price, BigDecimal fees, String orderId) {
        return new OrderResponse(
                format(price),
                format(fees),
                orderId
        );
    }

    private static String format(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }
}
