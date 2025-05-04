package com.unyx.ticketeira.application.usecases.sector;

import com.unyx.ticketeira.application.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.application.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Sector;
import com.unyx.ticketeira.domain.repository.SectorRepository;
import com.unyx.ticketeira.domain.util.AuthorizationValidator;
import com.unyx.ticketeira.domain.util.ConvertDTO;
import org.springframework.stereotype.Service;

@Service
public class AddSectorUseCase {
    private final SectorRepository sectorRepository;
    private final AuthorizationValidator authorizationValidator;

    public AddSectorUseCase(SectorRepository sectorRepository, AuthorizationValidator authorizationValidator) {
        this.sectorRepository = sectorRepository;
        this.authorizationValidator = authorizationValidator;
    }

    public SectorCreateResponse execute(String eventId, String userId, SectorCreateRequest dto){

        Event eventExists = authorizationValidator.validateEventProducer(eventId, userId);

        Sector newSector = ConvertDTO.convertSectorToModel(dto);
        newSector.setEvent(eventExists);

        Sector savedSector = sectorRepository.save(newSector);

        return new SectorCreateResponse(
                savedSector.getId(),
                savedSector.getName(),
                savedSector.getCapacity(),
                savedSector.getDescription(),
                "Success created sector"
        );
    }
}
