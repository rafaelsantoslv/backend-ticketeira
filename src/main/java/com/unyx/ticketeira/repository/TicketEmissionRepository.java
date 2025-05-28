package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.TicketEmission;
import com.unyx.ticketeira.repository.projection.SummaryProjection;
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


    @Query(value = """
        SELECT 
          COUNT(te.id) AS totalSold,
          AVG(te.unit_price) AS ticketMedium,
          SUM(te.unit_price) AS totalRevenue
        FROM ticket_emission te
        JOIN batches b ON te.batche_id = b.id
        JOIN sectors s ON b.sector_id = s.id
        WHERE s.event_id = :eventId
          AND te.status = 'VALID'
    """, nativeQuery = true)
    SummaryProjection getSummaryByEventId(@Param("eventId") String eventId);
}
