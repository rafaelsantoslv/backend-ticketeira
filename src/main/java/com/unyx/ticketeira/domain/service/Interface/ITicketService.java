package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITicketService {
    Ticket create(Ticket ticket);
    Ticket update(String id, Ticket ticket);
    void delete(String id);
    Optional<Ticket> findById(String id);
    List<Ticket> findByEvent(String eventId);
    List<Ticket> findBySector(String sectorId);
    List<Ticket> findByBatch(String batchId);
    boolean checkAvailability(String id);
    Ticket updatePrice(String id, BigDecimal price);
}
