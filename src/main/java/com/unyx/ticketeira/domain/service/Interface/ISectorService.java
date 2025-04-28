package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.Sector;

import java.util.List;
import java.util.Optional;

public interface ISectorService {
    Sector create(Sector sector);
    Sector update(String id, Sector sector);
    void delete(String id);
    Optional<Sector> findById(String id);
    List<Sector> findByEvent(String eventId);
    Sector updateCapacity(String id, Integer capacity);
    boolean checkAvailability(String id);
    void assignNumberedSeats(String sectorId, List<String> seats);
}
