package com.unyx.ticketeira.dto.event;

import com.unyx.ticketeira.dto.PaginationResponse;

import java.util.List;

public record EventsResponse(
        List<EventMeListAllByProducerResponse> events,
        PaginationResponse pagination
) {
}
