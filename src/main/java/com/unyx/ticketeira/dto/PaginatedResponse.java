package com.unyx.ticketeira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedResponse<T> {
    private List<T> data;
    private long total;
    private int page;
    private int limit;
    private int pages;
}
