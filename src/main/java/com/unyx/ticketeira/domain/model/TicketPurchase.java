package com.unyx.ticketeira.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets_purchases")
@EqualsAndHashCode(of="id")
public class TicketPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    private Integer quantity;

    private double amountPaid;

    @JoinColumn(name = "transaction_id")
    private String transactionId;

    private String paymentStatus;

    private LocalDateTime purchaseDate;
}
