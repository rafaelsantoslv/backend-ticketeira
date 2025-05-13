package com.unyx.ticketeira.dto.batch;

public record BatchCreateRequest(String name, int quantity, Double price, boolean requiresIdentification) {
}
