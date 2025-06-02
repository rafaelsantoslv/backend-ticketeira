package com.unyx.ticketeira.model;

import lombok.Data;

@Data
public class OrderItemRequest {
    private String sectorId;
    private String batchId;
    private int quantity;
}


