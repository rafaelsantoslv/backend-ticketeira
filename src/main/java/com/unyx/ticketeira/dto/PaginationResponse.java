package com.unyx.ticketeira.dto;

public record PaginationResponse(
        long total,
        int page,
        int limit,
        int pages
) {
}
