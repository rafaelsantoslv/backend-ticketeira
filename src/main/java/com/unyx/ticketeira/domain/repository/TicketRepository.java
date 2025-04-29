package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByEventId(String eventId);
    List<Ticket> findBySectorId(String sectorId);
    List<Ticket> findByBatchId(String batchId);
    List<Ticket> findByEventIdAndIsActive(String eventId, Boolean isActive);
}