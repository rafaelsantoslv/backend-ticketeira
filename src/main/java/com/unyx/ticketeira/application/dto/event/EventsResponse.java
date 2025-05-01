package com.unyx.ticketeira.application.dto.event;

import com.unyx.ticketeira.application.dto.PaginationResponse;

import java.util.List;

public record EventsResponse(
        List<EventListAllByProducerResponse> events,
        PaginationResponse pagination
) {
}
