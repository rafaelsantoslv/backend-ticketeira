package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Sector;
import com.unyx.ticketeira.repository.projection.SectorBatchProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, String> {

    List<Sector> findByEventId(String eventId);

    List<Sector> findAllByEventId(String eventId);

    @Query(value = """
        SELECT
          s.id AS sectorId,
          s.name AS sectorName,
          s.description AS sectorDescription,
          s.capacity AS sectorCapacity,
          b.id AS batchId,
          b.name AS batchName,
          b.price AS batchPrice,
          b.quantity AS batchQuantity,
          SUM((te.status = 'VALID')::int) AS batchSold,
          SUM(CASE WHEN te.status = 'VALID' THEN te.unit_price ELSE 0 END) AS batchRevenue
        FROM sectors s
        LEFT JOIN batches b ON b.sector_id = s.id
        LEFT JOIN ticket_emission te ON te.batche_id = b.id
        WHERE s.event_id = :eventId
        GROUP BY s.id, b.id
        ORDER BY s.id, b.id
    """, nativeQuery = true)
    List<SectorBatchProjection> getSectorAndBatchByEventId(@Param("eventId") String eventId);
}