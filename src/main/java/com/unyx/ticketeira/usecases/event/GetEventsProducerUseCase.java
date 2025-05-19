package com.unyx.ticketeira.usecases.event;

import com.unyx.ticketeira.dto.PaginationResponse;
import com.unyx.ticketeira.dto.event.EventMeListAllByProducerResponse;
import com.unyx.ticketeira.dto.event.EventsResponse;
import com.unyx.ticketeira.model.Event;
import com.unyx.ticketeira.repository.EventRepository;


import com.unyx.ticketeira.repository.TicketEmissionRepository;
import com.unyx.ticketeira.service.CloudflareStorageService;
import com.unyx.ticketeira.util.ConvertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class GetEventsProducerUseCase {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketEmissionRepository ticketEmissionRepository;

    @Autowired
    private CloudflareStorageService cloudflareStorageService;


    public EventsResponse execute(String userId, int page, int limit) {
        int pageIndex = page - 1;

        Page<Event> eventPage = eventRepository.findAllByCreatorId(userId, PageRequest.of(page, limit));

        List<String> eventIds = eventPage.getContent().stream()
                .map(Event::getId)
                .toList();

        List<Object[]> counts = ticketEmissionRepository.countByEventIds(eventIds, "OK");

        Map<String, Long> soldQuantities = counts.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],  // eventId
                        row -> (Long) row[1]     // count
                ));

        List<EventMeListAllByProducerResponse> events = eventPage.getContent().stream()
                .map(event -> {

                    Long soldQuantity = soldQuantities.getOrDefault(event.getId(), 0L);
                    String urlImage = cloudflareStorageService.getPublicUrl(event.getImageUrl());
                    event.setImageUrl(urlImage);
                    return ConvertDTO.convertEventToDto(event, soldQuantity);
                })
                .toList();

        PaginationResponse pagination = new PaginationResponse(
                eventPage.getTotalElements(),  // total de registros
                page,                         // página atual (mantém o número original)
                limit,                        // itens por página
                eventPage.getTotalPages()     // total de páginas
        );
        return new EventsResponse(events, pagination);

    }

}
