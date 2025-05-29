package com.unyx.ticketeira.dto.event;

import com.unyx.ticketeira.dto.batch.BatchDTO;
import com.unyx.ticketeira.dto.event.dto.EventDTO;

import com.unyx.ticketeira.dto.event.dto.SectorsDTO;
import com.unyx.ticketeira.dto.sector.SectorDTO;

import java.util.List;

public record EventDetailsResponse(
        EventDTO event,
        List<SectorDTO> sectors
) {
}
