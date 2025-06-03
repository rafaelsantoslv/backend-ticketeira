package com.unyx.ticketeira.repository.projection;

public interface SectorBatchProjection {
    String getSectorId();
    String getSectorName();
    String getSectorDescription();
    Integer getSectorCapacity();
    String getBatchId();
    String getBatchName();
    Double getBatchPrice();
    Integer getBatchQuantity();
    Integer getBatchSold();
    Double getBatchRevenue();
}
