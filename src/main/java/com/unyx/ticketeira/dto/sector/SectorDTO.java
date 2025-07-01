package com.unyx.ticketeira.dto.sector;

import com.unyx.ticketeira.dto.batch.BatchDTO;

import java.util.List;

public record SectorDTO(
        String id,
        String name,
        int capacity,
        List<BatchDTO> batches
) {
}
