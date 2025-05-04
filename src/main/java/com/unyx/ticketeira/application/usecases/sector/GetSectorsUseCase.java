package com.unyx.ticketeira.application.usecases.sector;

import com.unyx.ticketeira.application.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Sector;
import com.unyx.ticketeira.domain.repository.EventRepository;
import com.unyx.ticketeira.domain.repository.SectorRepository;
import com.unyx.ticketeira.domain.util.AuthorizationValidator;
import com.unyx.ticketeira.domain.util.ConvertDTO;
import com.unyx.ticketeira.exception.EventNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSectorsUseCase {
    private final EventRepository eventRepository;
    private final SectorRepository sectorRepository;
    private final AuthorizationValidator authorizationValidator;

    public GetSectorsUseCase(EventRepository eventRepository, SectorRepository sectorRepository, AuthorizationValidator authorizationValidator) {
        this.eventRepository = eventRepository;
        this.sectorRepository = sectorRepository;
        this.authorizationValidator = authorizationValidator;
    }

    public List<SectorListAllByEventResponse> execute(String eventId, String userId){
        authorizationValidator.validateEventProducer(eventId, userId);
        List<Sector> sectorList = sectorRepository.findAllByEventId(eventId);
        return sectorList.stream().map(ConvertDTO::convertSectorToDto).toList();
    }
}
