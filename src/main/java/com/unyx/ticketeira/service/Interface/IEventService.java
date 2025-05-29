package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.PaginetedResponse;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.dto.event.dto.EventListDTO;

public interface IEventService {
    EventCreateResponse createEvent(String userId, EventCreateRequest dto);
    PaginetedResponse<EventListDTO> listEventsByProducer(String userId, int page, int limit);
    PaginetedResponse<EventListDTO> listAllEventsPublished(int page, int limit);
    void getDashboardInfo(String eventId);

}
