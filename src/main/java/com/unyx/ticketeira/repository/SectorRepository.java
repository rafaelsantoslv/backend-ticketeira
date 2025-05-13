package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, String> {
    List<Sector> findAllByEventId(String eventId);

}