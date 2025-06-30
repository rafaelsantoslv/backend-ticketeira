package com.unyx.ticketeira.dto.order;

import java.util.List;

public record OrderItemRequest(
        String sectorId,
        String batchId,
        int quantity
) {
}


