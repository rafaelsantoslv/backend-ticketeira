package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.PaginetedResponse;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.model.Event;

public interface IEventService {
    EventCreateResponse createEvent(String userId, EventCreateRequest dto);
    PaginetedResponse<EventDTO> listEventsByProducer(String userId, int page, int limit);
    PaginetedResponse<EventDTO> listAllEventsPublished(int page, int limit);
    EventDetailsResponse getEventDetails(String eventId);
//    void getDashboardInfo(String eventId);


    Event validateAndGetEvent(String eventId);
}
