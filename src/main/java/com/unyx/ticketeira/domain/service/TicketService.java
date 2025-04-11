package com.unyx.ticketeira.domain.service;

import com.unyx.ticketeira.domain.implement.PaymentGateway;
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
    private final PaymentGateway paymentGateway;

    public TicketService(TicketRepository ticketRepository, TicketPurchaseRepository ticketPurchaseRepository, PaymentGateway paymentGateway) {
        this.ticketRepository = ticketRepository;
        this.ticketPurchaseRepository = ticketPurchaseRepository;
        this.paymentGateway = paymentGateway;
    }

    public TicketPurchase purchaseTicket(Long ticketId, Integer quantity, User buyer) {
        // Busca o ticket pelo ID
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EventNotFoundException("Ticket with ID " + ticketId + " not found"));

        // Verifica se há tickets disponíveis
        if (ticket.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough tickets available.");
        }

        //Calcula o total do pagamento
        double totalAmount = Math.round(ticket.getPrice() * quantity);

        boolean paymentSuccessful = paymentGateway.processPayment(totalAmount, buyer.getEmail());

        if(!paymentSuccessful) {
            throw new IllegalArgumentException("Payment failed. Please try again.");
        }

        ticket.setQuantity(ticket.getQuantity() - quantity);
        ticketRepository.save(ticket);

        // Cria a compra
        TicketPurchase purchase = new TicketPurchase();
        purchase.setUser(buyer);
        purchase.setTicket(ticket);
        purchase.setQuantity(quantity);
        purchase.setAmountPaid(totalAmount);
        purchase.setPaymentStatus("Paid");
        purchase.setTransactionId("123321");
        purchase.setPurchaseDate(LocalDateTime.now());

        return ticketPurchaseRepository.save(purchase);
    }

   // public void processSuccessfulPayment(String paymentIntentId, Long ticketId, Integer quantity, User buyer) {
   //     // Busca o ticket pelo ID
   //     Ticket ticket = ticketRepository.findById(ticketId)
   //             .orElseThrow(() -> new EventNotFoundException("Ticket with ID " + ticketId + " not found"));
//
   //     // Verifica se há tickets disponíveis
   //     if (ticket.getQuantity() < quantity) {
   //         throw new IllegalArgumentException("Not enough tickets available.");
   //     }
//
   //     // Atualiza a quantidade de tickets disponíveis
   //     ticket.setQuantity(ticket.getQuantity() - quantity);
   //     ticketRepository.save(ticket);
//
   //     // Cria a compra
   //     TicketPurchase purchase = new TicketPurchase();
   //     purchase.setUser(buyer);
   //     purchase.setTicket(ticket);
   //     purchase.setQuantity(quantity);
   //     purchase.setPurchaseDate(LocalDateTime.now());
//
   //     ticketPurchaseRepository.save(purchase);
//
   //     System.out.println("Compra processada com sucesso para o PaymentIntent: " + paymentIntentId);
   // }
}
