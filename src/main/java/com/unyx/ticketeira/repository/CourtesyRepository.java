package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Courtesy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourtesyRepository extends JpaRepository<Courtesy, String> {
    List<Courtesy> findCourtesiesByEventId(String eventId);
}
