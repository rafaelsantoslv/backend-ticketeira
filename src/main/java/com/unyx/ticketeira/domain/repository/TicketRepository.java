package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByEvent(Event event);
}
