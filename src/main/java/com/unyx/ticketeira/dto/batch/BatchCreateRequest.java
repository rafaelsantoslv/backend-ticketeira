package com.unyx.ticketeira.dto.batch;

import java.math.BigDecimal;

public record BatchCreateRequest(String name, int quantity, BigDecimal price, boolean requiresIdentification) {
}
