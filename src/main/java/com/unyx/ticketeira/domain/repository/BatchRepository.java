package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, String> {
    List<Batch> findByEventId(String eventId);
    List<Batch> findByEventIdAndIsActive(String eventId, Boolean isActive);

}