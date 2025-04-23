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
@Table(name = "events")
@EqualsAndHashCode(of="id")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String description;

    @Column(name = "name_locale")
    private String nameLocale;

    private String address;

    private String classification;

    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private User producer;
}
