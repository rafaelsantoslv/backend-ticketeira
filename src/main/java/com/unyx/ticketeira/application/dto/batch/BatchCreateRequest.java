package com.unyx.ticketeira.application.dto.batch;

public record BatchCreateRequest(String name, int quantity, Double price, boolean requiresIdentification) {
}
