package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, String> {
    List<CheckIn> findByEventId(String eventId);
    List<CheckIn> findByOrderItem_TicketCode(String ticketCode);

    @Query("SELECT COUNT(c) FROM CheckIn c WHERE c.event.id = :eventId")
    Long countCheckInsByEvent(@Param("eventId") String eventId);

    @Query("SELECT c.location, COUNT(c) FROM CheckIn c WHERE c.event.id = :eventId GROUP BY c.location")
    List<Object[]> countCheckInsByLocation(@Param("eventId") String eventId);
}