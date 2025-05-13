package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.TicketEmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<TicketEmission, String> {

}