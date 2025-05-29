package com.unyx.ticketeira.dto.event.dto;

import java.util.List;

public record SectorsDTO(
        String id,
        String name,
        String description,
        List<BatchesDTO> batches
) {
}
