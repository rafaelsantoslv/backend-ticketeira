package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.TicketPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPurchaseRepository extends JpaRepository<TicketPurchase, Long> {
}
