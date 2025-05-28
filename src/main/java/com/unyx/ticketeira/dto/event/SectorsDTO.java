package com.unyx.ticketeira.dto.event;

import java.util.List;

public record SectorsDTO(
        String id,
        String name,
        String description,
        List<BatchesDTO> batches
) {
}
