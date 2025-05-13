package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.TicketEmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketEmissionRepository extends JpaRepository<TicketEmission, String> {

    @Query("""
        SELECT te.batch.sector.event.id as eventId, COUNT(te) as total
        FROM TicketEmission te
        WHERE te.batch.sector.event.id IN :eventIds
        AND te.status = :status
        GROUP BY te.batch.sector.event.id
    """)
    List<Object[]> countByEventIds(
            @Param("eventIds") List<String> eventIds,
            @Param("status") String status
    );
}
