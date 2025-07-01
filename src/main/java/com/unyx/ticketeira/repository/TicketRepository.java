package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Ticket;
import com.unyx.ticketeira.repository.projection.SummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    long countByEventId(String eventId);
    long countByBatchId(String batchId);
    List<Ticket> findByEventId(String eventId);

}
