package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.PaginatedResponse;
import com.unyx.ticketeira.dto.batch.BatchDTO;
import com.unyx.ticketeira.dto.coupon.CouponDTO;
import com.unyx.ticketeira.dto.courtesy.CourtesyDTO;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.dto.event.dto.BatchesDTO;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.dto.payment.PaymentMetricsDTO;
import com.unyx.ticketeira.dto.sector.SectorDTO;
import com.unyx.ticketeira.dto.ticket.CheckinStatusMetricsDTO;
import com.unyx.ticketeira.dto.ticket.TicketDTO;
import com.unyx.ticketeira.exception.*;
import com.unyx.ticketeira.mapper.*;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.model.enums.PaymentMethod;
import com.unyx.ticketeira.model.enums.StatusTicket;
import com.unyx.ticketeira.model.enums.TicketType;
import com.unyx.ticketeira.repository.*;
import com.unyx.ticketeira.service.Interface.*;
import com.unyx.ticketeira.util.ConvertDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.unyx.ticketeira.constant.SystemMessages.*;


@AllArgsConstructor
@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CloudflareService cloudflareService;
    private final TicketRepository ticketRepository;
    private final ISectorService sectorService;
    private final IBatchService batchService;
    private final ITicketService ticketService;
    private final ICouponService couponService;
    private final ICourtesyService courtesyService;
    private final IPaymentService paymentService;


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

    public PaginatedResponse<EventDTO> getEventsPublished(int page, int limit) {
        int pageIndex = page - 1;

        Page<Event> eventsPublished = eventRepository.findAllByIsPublished(true, PageRequest.of(page, limit, Sort.by("startDate").ascending()));

        List<EventDTO> eventConvertDto = eventsPublished.getContent()
                .stream()
                .map(event -> {
                    long soldQuantity = ticketRepository.countByEventId(event.getId());
                    return ConvertDTO.convertEventToDto(event, soldQuantity);
                })
                .toList();

        return new PaginatedResponse<>(
                eventConvertDto,
                eventsPublished.getTotalElements(),
                page,
                limit,
                eventsPublished.getTotalPages()
        );

    }

    public EventDetailsDTO getEventDetails(String eventId) {

        Event event = getEventById(eventId);
        List<Sector> sectors = sectorService.getSectorsByEventId(eventId);

        Map<String, String> sectorIdToName = sectors.stream()
                .collect(Collectors.toMap(Sector::getId, Sector::getName));

        List<SectorDTO> sectorDTOS = sectors.stream().map(sector -> {
            List<Batch> batches = batchService.getBatchesBySectorId(sector.getId());
            List<BatchDTO> batchDTOs = batches.stream().map(batch -> {
                long sold = ticketService.getCountByBatchId(batch.getId());
                return BatchMapper.toDTO(batch, sold);
            }).toList();

            return SectorMapper.toDTO(sector, batchDTOs);
        }).toList();

        List<Coupon> coupons = couponService.getCouponsByEventId(eventId);

        List<CouponDTO> couponDTOS = coupons.stream().map(CouponMapper::toDTO).toList();

        List<Courtesy> courtesies = courtesyService.getCourtesiesByEventId(eventId);

        List<CourtesyDTO> courtesyDTOS = courtesies.stream().map(CourtesyMapper::toDTO).toList();

        List<Ticket> tickets = ticketService.getTicketsByEvent(eventId);

        List<TicketDTO> ticketDTOS = tickets.stream()
                .map(ticket -> TicketMapper.toDTO(ticket, sectorIdToName.get(ticket.getSectorId())))
                .toList();

        List<Payment> payments = paymentService.findByEventId(eventId);

        MetricsDTO metrics = buildMetrics(tickets, payments);

        return EventMapper.toDto(event, sectorDTOS, couponDTOS, courtesyDTOS, ticketDTOS, metrics);
    }

    public Event validateAndGetEvent(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
        if (!event.getIsPublished()) {
            throw new AccessDeniedException(EVENT_ACCESS_DENIED);
        }
        return event;
    }

    private Event getEventById(String eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
    }


    private MetricsDTO buildMetrics(List<Ticket> tickets, List<Payment> payments) {
        int totalTickets = tickets.size();

        int totalSales = (int) tickets.stream()
                .filter(ticket -> ticket.getTicketType() == TicketType.REGULAR)
                .count();

        BigDecimal totalValue = tickets.stream()
                .filter(ticket -> ticket.getTicketType() == TicketType.REGULAR)
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageTicket = totalSales > 0
                ? totalValue.divide(BigDecimal.valueOf(totalSales), RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        int totalCheckins = (int) tickets.stream()
                .filter(ticket -> ticket.getStatus() == StatusTicket.VALIDATED)
                .count();

        BigDecimal creditCard = payments.stream()
                .filter(p -> p.getMethod() == PaymentMethod.CREDIT_CARD)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pix = payments.stream()
                .filter(p -> p.getMethod() == PaymentMethod.PIX)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPayments = creditCard.add(pix);

        int validated = (int) tickets.stream()
                .filter(ticket -> ticket.getStatus() == StatusTicket.VALIDATED)
                .count();
        int pending = (int) tickets.stream()
                .filter(ticket -> ticket.getStatus() == StatusTicket.PENDING)
                .count();
        int cancelled = (int) tickets.stream()
                .filter(ticket -> ticket.getStatus() == StatusTicket.CANCELLED)
                .count();

        int checkinTotal = validated + pending + cancelled;

        return new MetricsDTO(
                totalSales,
                totalTickets,
                averageTicket,
                totalCheckins,
                totalValue,
                new PaymentMetricsDTO(creditCard, pix, totalPayments),
                new CheckinStatusMetricsDTO(checkinTotal, validated, pending, cancelled)
        );
    }




}
