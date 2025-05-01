package com.unyx.ticketeira.application.dto;

public record PaginationResponse(
        long total,
        int page,
        int limit,
        int pages
) {
}
