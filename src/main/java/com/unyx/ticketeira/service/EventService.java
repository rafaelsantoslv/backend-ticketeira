package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.PaginatedResponse;
import com.unyx.ticketeira.dto.batch.BatchDTO;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.dto.sector.SectorDTO;
import com.unyx.ticketeira.exception.*;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.repository.*;
import com.unyx.ticketeira.service.Interface.IEventService;
import com.unyx.ticketeira.util.ConvertDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unyx.ticketeira.constant.SystemMessages.*;


@AllArgsConstructor
@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CloudflareService cloudflareService;
    private final TicketRepository ticketRepository;



    public EventCreateResponse createEvent(String userId, EventCreateRequest dto) {
        User userExists = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User incorrect"));

        if(!userExists.getRole().getName().equalsIgnoreCase("PRODUCER")) {
            throw new UnauthorizedException(ROLE_NOT_FOUND);
        }

        UploadInfo uploadInfo = cloudflareService.generateUploadUrl();

        Event convertEvent = ConvertDTO.convertEvent(
                dto,
                uploadInfo.getObjectKey(),
                userExists
        );

        Event addEvent = eventRepository.save(convertEvent);

        return new EventCreateResponse(addEvent.getId(), uploadInfo.getUploadKey(), EVENT_SUCCESS);
    }

    public PaginatedResponse<EventDTO> getEventsByProducer(String userId, int page, int limit) {
        int pageIndex = page - 1;

        Page<Event> eventPage = eventRepository.findAllByCreatorId(userId, PageRequest.of(page, limit));

        List<String> eventIds = eventPage.getContent().stream()
                .map(Event::getId)
                .toList();



        List<EventDTO> eventConvertDto = eventPage.getContent().stream()
                .map(event -> {
                    long soldQuantity = ticketRepository.countByEventId(event.getId());
                    String urlImage = cloudflareService.getPublicUrl(event.getImageUrl());
                    event.setImageUrl(urlImage);
                    return ConvertDTO.convertEventToDto(event, soldQuantity);
                })
                .toList();

        return new PaginatedResponse<>(
                eventConvertDto,
                eventPage.getTotalElements(),
                page,
                limit,
                eventPage.getTotalPages()
        );
    }

//    public PaginatedResponse<EventDTO> listAllEventsPublished(int page, int limit) {
//        int pageIndex = page - 1;
//
//        Page<Event> eventsPublished = eventRepository.findAllByIsPublished(true, PageRequest.of(page, limit, Sort.by("startDate").ascending()));
//
//        List<EventDTO> eventConvertDto = eventsPublished.getContent()
//                .stream()
//                .map(ConvertDTO::convertEventToDto)
//                .toList();
//
//        return new PaginatedResponse<>(
//                eventConvertDto,
//                eventsPublished.getTotalElements(),
//                page,
//                limit,
//                eventsPublished.getTotalPages()
//        );
//
//    }

//    public EventDetailsResponse getEventDetails(String eventId) {
//
//        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
//
//        if(!event.getIsPublished()) {
//            throw new AccessDeniedException(EVENT_ACCESS_DENIED);
//        }
//
//        List<Sector> sectors = sectorRepository.findByEventId(eventId);
//        if (sectors == null || sectors.isEmpty()) {
//            throw new SectorNotFoundException(SECTOR_NOT_FOUND);
//        }
//
//        List<SectorDTO> sectorDTOs = sectors.stream().map(sector -> {
//            List<Batch> batches = batchRepository.findBySectorIdAndIsActive(sector.getId(), true);
//            if (batches == null || batches.isEmpty()) {
//                throw new BatchNotFoundException( BATCH_NOT_FOUND + " " + sector.getName());
//            }
//            List<BatchDTO> batchDTOs = batches.stream()
//                    .map(ConvertDTO::convertBatchRespToDto)
//                    .toList();
//            return ConvertDTO.convertSectorRespToDto(sector, batchDTOs);
//        }).toList();
//
//        EventDTO eventConvert = ConvertDTO.convertEventToDto(event);
//
//        return new EventDetailsResponse(
//                eventConvert,
//                sectorDTOs
//        );
//
//    }

    public Event validateAndGetEvent(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
        if (!event.getIsPublished()) {
            throw new AccessDeniedException(EVENT_ACCESS_DENIED);
        }
        return event;
    }




}
