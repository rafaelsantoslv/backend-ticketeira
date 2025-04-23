package com.unyx.ticketeira.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
@EqualsAndHashCode(of="id")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "ticket_name")
    private String ticketName;

    private Double price;

    private Integer quantity;
    @Column(name = "is_active")
    private Boolean isActive;

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne
    private Event event;
}
