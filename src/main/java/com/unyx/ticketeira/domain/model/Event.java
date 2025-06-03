package com.unyx.ticketeira.domain.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "location_city")
    private String locationCity;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "location_state")
    private String locationState;

    @Column(name = "location_zip")
    private String locationZip;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
}
