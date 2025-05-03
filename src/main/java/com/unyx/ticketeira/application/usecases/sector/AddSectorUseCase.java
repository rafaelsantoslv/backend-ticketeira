package com.unyx.ticketeira.application.usecases.sector;

import com.unyx.ticketeira.application.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.application.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Sector;
import com.unyx.ticketeira.domain.repository.EventRepository;
import com.unyx.ticketeira.domain.repository.SectorRepository;
import com.unyx.ticketeira.exception.EventNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddSectorUseCase {
    private final SectorRepository sectorRepository;
    private final EventRepository eventRepository;

    public AddSectorUseCase(EventRepository eventRepository, SectorRepository sectorRepository) {
        this.eventRepository = eventRepository;
        this.sectorRepository = sectorRepository;
    }

    public SectorCreateResponse execute(String eventId, SectorCreateRequest dto){
        Event eventExists = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found"));

        Sector newSector = new Sector();
        newSector.setName(dto.name());
        newSector.setEvent(eventExists);
        newSector.setDescription(dto.description());
        newSector.setCapacity(dto.capacity());

        Sector response = sectorRepository.save(newSector);

        return new SectorCreateResponse(response.getId(), response.getName(), response.getCapacity(), response.getDescription(), "Success created sector");
    }
}
