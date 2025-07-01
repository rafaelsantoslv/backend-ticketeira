package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.PaginatedResponse;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.model.Event;

public interface IEventService {
    EventCreateResponse createEvent(String userId, EventCreateRequest dto);
    PaginatedResponse<EventDTO> getEventsByProducer(String userId, int page, int limit);
    PaginatedResponse<EventDTO> getEventsPublished(int page, int limit);
    EventDetailsDTO getEventDetails(String eventId);
    EventUpdateResponse updateEvent(String userId, String eventId, EventUpdateRequest dto);
    Event validateAndGetEvent(String eventId);
}
