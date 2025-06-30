package com.unyx.ticketeira.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtDTO(String token, @JsonProperty("valid_until") String validUntil) {

}
