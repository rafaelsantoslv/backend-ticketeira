package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, String> {
    List<Sector> findByEventId(String eventId);

}