package com.unyx.ticketeira.usecases.sector;

import com.unyx.ticketeira.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.model.Sector;
import com.unyx.ticketeira.repository.SectorRepository;
import com.unyx.ticketeira.util.AuthorizationValidator;
import com.unyx.ticketeira.util.ConvertDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSectorsUseCase {
    private final SectorRepository sectorRepository;
    private final AuthorizationValidator authorizationValidator;

    public GetSectorsUseCase( SectorRepository sectorRepository, AuthorizationValidator authorizationValidator) {
        this.sectorRepository = sectorRepository;
        this.authorizationValidator = authorizationValidator;
    }

    public List<SectorListAllByEventResponse> execute(String eventId, String userId){
        authorizationValidator.validateEventProducer(eventId, userId);
        List<Sector> sectorList = sectorRepository.findAllByEventId(eventId);
        return sectorList.stream().map(ConvertDTO::convertSectorToDto).toList();
    }
}
