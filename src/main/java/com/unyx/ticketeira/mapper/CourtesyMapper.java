package com.unyx.ticketeira.mapper;

import com.unyx.ticketeira.dto.courtesy.CourtesyDTO;
import com.unyx.ticketeira.model.Courtesy;

public class CourtesyMapper {

    public static CourtesyDTO toDTO(Courtesy courtesy) {
        return new CourtesyDTO(
            courtesy.getId(),
            courtesy.getName(),
            courtesy.getEmail(),
            courtesy.getSent(),
            courtesy.getSector().getName(),
            courtesy.getCreatedAt()
        );
    }
}
