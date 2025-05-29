package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.PaginetedResponse;
import com.unyx.ticketeira.dto.batch.BatchDTO;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.dto.event.dto.BatchesDTO;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.dto.event.dto.SectorsDTO;
import com.unyx.ticketeira.dto.event.dto.SummaryDTO;
import com.unyx.ticketeira.dto.sector.SectorDTO;
import com.unyx.ticketeira.exception.*;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.repository.*;
import com.unyx.ticketeira.repository.projection.PaymentMethodSummaryProjection;
import com.unyx.ticketeira.repository.projection.SectorBatchProjection;
import com.unyx.ticketeira.repository.projection.SummaryProjection;
import com.unyx.ticketeira.service.Interface.IEventService;
import com.unyx.ticketeira.util.ConvertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {
    @Autowired
    private TicketEmissionRepository ticketEmissionRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudflareService cloudflareService;



    public EventCreateResponse createEvent(String userId, EventCreateRequest dto) {
        User userExists = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User incorrect"));

        if(!userExists.getRole().getName().equalsIgnoreCase("PRODUCER")) {
            throw new UnauthorizedException("Role incorrect");
        }

        UploadInfo uploadInfo = cloudflareService.generateUploadUrl();

        Event convertEvent = ConvertDTO.convertEvent(dto, uploadInfo.getObjectKey(), userExists);

        Event addEvent = eventRepository.save(convertEvent);

        return new EventCreateResponse(addEvent.getId(), uploadInfo.getUploadKey(), "Success created event");
    }

    public PaginetedResponse<EventDTO> listEventsByProducer(String userId, int page, int limit) {
        int pageIndex = page - 1;

        Page<Event> eventPage = eventRepository.findAllByCreatorId(userId, PageRequest.of(page, limit));

        List<String> eventIds = eventPage.getContent().stream()
                .map(Event::getId)
                .toList();

        List<Object[]> counts = ticketEmissionRepository.countByEventIds(eventIds, "OK");

        List<EventDTO> eventConvertDto = eventPage.getContent().stream()
                .map(event -> {
                    String urlImage = cloudflareService.getPublicUrl(event.getImageUrl());
                    event.setImageUrl(urlImage);
                    return ConvertDTO.convertEventToDto(event);
                })
                .toList();

        return new PaginetedResponse<>(
                eventConvertDto,
                eventPage.getTotalElements(),
                page,
                limit,
                eventPage.getTotalPages()
        );
    }

    public PaginetedResponse<EventDTO> listAllEventsPublished(int page, int limit) {
        int pageIndex = page - 1;

        Page<Event> eventsPublished = eventRepository.findAllByIsPublished(true, PageRequest.of(page, limit, Sort.by("startDate").ascending()));

        List<EventDTO> eventConvertDto = eventsPublished.getContent()
                .stream()
                .map(ConvertDTO::convertEventToDto)
                .toList();

        return new PaginetedResponse<>(
                eventConvertDto,
                eventsPublished.getTotalElements(),
                page,
                limit,
                eventsPublished.getTotalPages()
        );

    }

    public EventDetailsResponse getEventDetails(String eventId) {

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Evento não encontrado"));

        if(!event.getIsPublished()) {
            throw new AccessDeniedException("Evento não disponível para visualização");
        }

        List<Sector> sectors = sectorRepository.findByEventId(eventId);
        if (sectors == null || sectors.isEmpty()) {
            throw new SectorNotFoundException("Não foi encontrado setores para esse evento");
        }

        List<SectorDTO> sectorDTOs = sectors.stream().map(sector -> {
            List<Batch> batches = batchRepository.findBySectorIdAndIsActive(sector.getId(), true);
            if (batches == null || batches.isEmpty()) {
                throw new BatchNotFoundException("Não foi encontrado lote para o setor: " + sector.getName());
            }
            List<BatchDTO> batchDTOs = batches.stream()
                    .map(ConvertDTO::convertBatchRespToDto)
                    .toList();
            return ConvertDTO.convertSectorRespToDto(sector, batchDTOs);
        }).toList();

        EventDTO eventConvert = ConvertDTO.convertEventToDto(event);

        return new EventDetailsResponse(
                eventConvert,
                sectorDTOs
        );

    }

//    public void getDashboardInfo(String eventId) {
//        SummaryProjection projection = ticketEmissionRepository.getSummaryByEventId(eventId);
//        SummaryDTO summaryDTO = projection != null
//                ? new SummaryDTO(
//                projection.getTotalSold() != null ? projection.getTotalSold() : 0L,
//                projection.getTicketMedium() != null ? projection.getTicketMedium() : 0.0,
//                projection.getTotalRevenue() != null ? projection.getTotalRevenue() : 0.0
//        )
//                : new SummaryDTO(0L, 0.0, 0.0);
//
//        Long countCheckins = checkInRepository.countByEventId(eventId);
//        List<SectorBatchProjection> projection1 = sectorRepository.getSectorAndBatchByEventId(eventId);
//
//        List<SectorsDTO> sectors = projection1.stream()
//                .collect(Collectors.groupingBy(
//                        SectorBatchProjection::getSectorId,
//                        Collectors.collectingAndThen(
//                                Collectors.toList(),
//                                list -> {
//                                    SectorBatchProjection first = list.get(0);
//
//                                    List<BatchesDTO> batches = list.stream()
//                                            .filter(p -> p.getBatchId() != null)
//                                            .map(p -> new BatchesDTO(
//                                                    p.getBatchId(),
//                                                    p.getBatchName(),
//                                                    p.getBatchPrice(),
//                                                    p.getBatchQuantity(),
//                                                    p.getBatchSold() != null ? p.getBatchSold() : 0,
//                                                    p.getBatchRevenue() != null ? p.getBatchRevenue() : 0.0
//                                            ))
//                                            .collect(Collectors.toList());
//
//                                    return new SectorsDTO(
//                                            first.getSectorId(),
//                                            first.getSectorName(),
//                                            first.getSectorDescription(),
//                                            batches
//                                    );
//                                }
//                        )
//                ))
//                .values()
//                .stream()
//                .toList();
//
//
//
//        List<PaymentMethodSummaryProjection> summaries = paymentRepository.getPaymentSummaryByMethod(eventId);
//
//        System.out.println("Resumo de pagamentos por método:");
//        summaries.forEach(summary -> {
//            System.out.printf(
//                    "Método: %s | Pagamentos: %d | Valor total: R$%.2f | Quantidade vendida: %d%n",
//                    summary.getPaymentMethod(),
//                    summary.getTotalPayments(),
//                    summary.getTotalValue(),
//                    summary.getTotalSold()
//            );
//        });
//
//        System.out.printf("Total vendido: %d, Média do ingresso: %.2f, Receita total: %.2f%n, total checkados: %d",
//                summaryDTO.totalSold(),
//                summaryDTO.ticketMedium(),
//                summaryDTO.totalRevenue(),
//                countCheckins
//        );
//
//        System.out.println("\nDetalhes por setor:");
//        sectors.forEach(sector -> {
//            System.out.printf("Setor: %s (%s) - %s%n",
//                    sector.name(),
//                    sector.id(),
//                    sector.description());
//            sector.batches().forEach(batch ->
//                    System.out.printf("  - Lote: %s, Preço: R$%.2f, Vendidos: %d/%d, Receita: R$%.2f%n",
//                            batch.name(),
//                            batch.price(),
//                            batch.sold(),
//                            batch.quantity(),
//                            batch.revenue()
//                    )
//            );
//            System.out.println();
//        });
//
//    }

    private Map<String, Long> getSoldQuantities(List<String> eventIds) {
        List<Object[]> counts = ticketEmissionRepository.countByEventIds(eventIds, "OK");
        return counts.stream().collect(Collectors.toMap(
                row -> (String) row[0],
                row -> (Long) row[1]
        ));
    }


}
