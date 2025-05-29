package com.unyx.ticketeira.dto.sector;

import com.unyx.ticketeira.dto.batch.BatchDTO;

import java.util.List;

public record SectorDTO(
        String name,
        String description,
        List<BatchDTO> batches
) {
}
