package com.unyx.ticketeira.dto.order;

import java.util.List;

public record OrderRequest(
        String eventId,
        String coupon,
        List<OrderItemRequest> items
) {
}
