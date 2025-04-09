package com.unyx.ticketeira.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tab_event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String nameLocale;
    private String address;
    private String classification;
    private String category;

    private String coverImageUrl;
    private String mainImageUrl;

    private LocalDateTime eventDateTime;

    @ManyToOne
    private User producer;
}
