package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.PaginationResponse;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.model.Event;
import com.unyx.ticketeira.repository.projection.PaymentMethodSummaryProjection;
import com.unyx.ticketeira.repository.projection.SectorBatchProjection;
import com.unyx.ticketeira.repository.projection.SummaryProjection;
import com.unyx.ticketeira.util.ConvertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface IEventService {
    EventCreateResponse createEvent(String userId, EventCreateRequest dto);
    EventsResponse listEvents(String userId, int page, int limit);
    void getDashboardInfo(String eventId);

}
