package com.unyx.ticketeira.model;

import com.unyx.ticketeira.model.enums.StatusTicket;
import com.unyx.ticketeira.model.enums.TicketType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
@EqualsAndHashCode(of = "id")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(nullable = false)
    private StatusTicket status;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String ownerEmail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "batch_id", nullable = false)
    private String batchId;

    @Column(name = "sector_id", nullable = false)
    private String sectorId;

    @Column(nullable = false)
    private BigDecimal price;

    private Boolean checkedIn;

    private LocalDateTime checkedInAt;

    private String checkedInBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}