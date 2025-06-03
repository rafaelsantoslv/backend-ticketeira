package com.unyx.ticketeira.dto.event.dto;

import com.unyx.ticketeira.dto.batch.BatchDTO;

import java.util.List;

public record SectorsDTO(
        String id,
        String name,
        String description,
        List<BatchDTO> batches
) {
}
