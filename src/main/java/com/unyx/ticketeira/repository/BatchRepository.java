package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, String> {
   List<Batch>findAllBySectorId(String sectorId);

   List<Batch> findBySectorIdAndIsActive(String sectorId, boolean active);

}