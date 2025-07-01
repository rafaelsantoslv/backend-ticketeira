package com.unyx.ticketeira.model;

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
@Table(name = "courtesy")
@EqualsAndHashCode(of = "id")
public class Courtesy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String email;
    private Boolean sent;
    private LocalDateTime createdAt;

    @ManyToOne
    private Sector sector;

    @ManyToOne
    private Event event;

    @OneToOne
    private Ticket ticket;
}
