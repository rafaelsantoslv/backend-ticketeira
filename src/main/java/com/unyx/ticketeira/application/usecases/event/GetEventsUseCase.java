package com.unyx.ticketeira.application.usecases.event;

import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.repository.EventRepository;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GetEventsUseCase {
    private final EventRepository eventRepository;

    public GetEventsUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> execute(String userId, int page, int items) {
        return eventRepository.findAllByCreatorId(userId, PageRequest.of(page, items));

    }

    private boolean isValidStatus(String status) {
        return status.equals("active") || status.equals("upcoming") || status.equals("completed") || status.equals("canceled");
    }
}
