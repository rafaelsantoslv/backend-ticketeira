package com.unyx.ticketeira.domain.service;

import com.unyx.ticketeira.domain.model.Ticket;
import com.unyx.ticketeira.domain.model.TicketPurchase;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.TicketPurchaseRepository;
import com.unyx.ticketeira.domain.repository.TicketRepository;
import com.unyx.ticketeira.exception.EventNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketPurchaseRepository ticketPurchaseRepository;

    public TicketService(TicketRepository ticketRepository, TicketPurchaseRepository ticketPurchaseRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketPurchaseRepository = ticketPurchaseRepository;
    }

    public TicketPurchase purchaseTicket(Long ticketId, Integer quantity, User buyer) {
        // Busca o ticket pelo ID
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EventNotFoundException("Ticket with ID " + ticketId + " not found"));

        // Verifica se há tickets disponíveis
        if (ticket.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough tickets available.");
        }

        // Atualiza a quantidade de tickets disponíveis
        ticket.setQuantity(ticket.getQuantity() - quantity);
        ticketRepository.save(ticket);

        // Cria a compra
        TicketPurchase purchase = new TicketPurchase();
        purchase.setBuyer(buyer);
        purchase.setTicket(ticket);
        purchase.setQuantity(quantity);
        purchase.setPurchaseDate(LocalDateTime.now());

        return ticketPurchaseRepository.save(purchase);
    }
}
