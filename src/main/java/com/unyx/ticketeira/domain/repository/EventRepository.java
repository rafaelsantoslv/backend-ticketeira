package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByIsPublished(Boolean isPublished);
    List<Event> findByIsFeatured(Boolean isFeatured);

    List<Event> findAllByCreatorId(String creatorId, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.startDate >= :now AND e.isPublished = true ORDER BY e.startDate")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);

    @Query("SELECT e FROM Event e WHERE e.locationCity = :city AND e.locationState = :state AND e.isPublished = true")
    List<Event> findByLocation(@Param("city") String city, @Param("state") String state);

    @Query("SELECT e FROM Event e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Event> search(@Param("query") String query);
}