package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.exception.SectorNotFoundException;
import com.unyx.ticketeira.model.Event;
import com.unyx.ticketeira.model.Sector;
import com.unyx.ticketeira.repository.SectorRepository;
import com.unyx.ticketeira.service.Interface.ISectorService;
import com.unyx.ticketeira.util.AuthorizationValidator;
import com.unyx.ticketeira.util.ConvertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unyx.ticketeira.constant.SystemMessages.SECTOR_NOT_FOUND;
import static com.unyx.ticketeira.constant.SystemMessages.SECTOR_SUCCESS;

@Service
public class SectorService implements ISectorService {
    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private AuthorizationValidator authorizationValidator;

    public SectorCreateResponse createSector(String eventId, String userId, SectorCreateRequest dto) {
        Event eventExists = authorizationValidator.validateEventProducer(eventId, userId);

        Sector newSector = ConvertDTO.convertSectorToModel(dto);
        newSector.setEvent(eventExists);

        Sector savedSector = sectorRepository.save(newSector);

        return new SectorCreateResponse(
                savedSector.getId(),
                savedSector.getName(),
                savedSector.getCapacity(),
                savedSector.getDescription(),
                SECTOR_SUCCESS
        );
    }

    public List<SectorListAllByEventResponse> listAllSectors(String eventId, String userId){
        authorizationValidator.validateEventProducer(eventId, userId);
        List<Sector> sectorList = sectorRepository.findAllByEventId(eventId);
        return sectorList.stream().map(ConvertDTO::convertSectorToDto).toList();
    }

    public Sector validateSectorAndGetSector(String sectorId) {
        return sectorRepository.findById(sectorId).orElseThrow(
                () -> new SectorNotFoundException(SECTOR_NOT_FOUND)
        );
    }

    public List<Sector> getSectorsByEventId(String eventId){
        return sectorRepository.findAllByEventId(eventId);
    }

}
