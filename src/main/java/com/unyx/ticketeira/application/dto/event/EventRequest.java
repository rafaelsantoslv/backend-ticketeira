package com.unyx.ticketeira.application.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String nameLocale;

    @NotBlank
    private String address;

    @NotBlank
    private String classification;

    @NotBlank
    private String category;

    private String coverImageUrl;
    private String mainImageUrl;

    @NotNull
    private LocalDateTime eventDateTime;

}
