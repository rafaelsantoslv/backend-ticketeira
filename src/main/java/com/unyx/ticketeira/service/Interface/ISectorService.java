package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.dto.sector.SectorListAllByEventResponse;

import java.util.List;

public interface ISectorService {
    SectorCreateResponse createSector(String eventId, String userId, SectorCreateRequest dto);
    List<SectorListAllByEventResponse> listAllSectors(String eventId, String userId);
}
