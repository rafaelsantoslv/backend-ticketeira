package com.unyx.ticketeira.mapper;

import com.unyx.ticketeira.dto.batch.BatchDTO;
import com.unyx.ticketeira.dto.sector.SectorDTO;
import com.unyx.ticketeira.model.Sector;

import java.util.List;

public class SectorMapper {
    public static SectorDTO toDTO(Sector sector, List<BatchDTO> batches) {
        return new SectorDTO(
                sector.getId(),
                sector.getName(),
                sector.getCapacity(),
                batches
        );
    }
}
