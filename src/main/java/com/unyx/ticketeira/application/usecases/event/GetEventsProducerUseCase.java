package com.unyx.ticketeira.application.usecases.event;

import com.unyx.ticketeira.application.dto.PaginationResponse;
import com.unyx.ticketeira.application.dto.event.EventListAllByProducerResponse;
import com.unyx.ticketeira.application.dto.event.EventsResponse;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.repository.EventRepository;


import com.unyx.ticketeira.domain.util.ConvertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class GetEventsProducerUseCase {
    private final EventRepository eventRepository;

    public GetEventsProducerUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventsResponse execute(String userId, int page, int limit) {
        int pageIndex = page - 1;

        Page<Event> eventPage = eventRepository.findAllByCreatorId(userId, PageRequest.of(page, limit));

        List<EventListAllByProducerResponse> events = eventPage.getContent()
                .stream()
                .map(ConvertDTO::convertEventToDto)
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
