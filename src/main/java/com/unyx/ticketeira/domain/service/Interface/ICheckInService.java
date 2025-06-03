package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.CheckIn;

import java.util.List;
import java.util.Optional;

public interface ICheckInService {
    CheckIn perform(CheckIn checkIn);
    Optional<CheckIn> findById(String id);
    List<CheckIn> findByEvent(String eventId);
    List<CheckIn> findByTicket(String ticketCode);
    boolean validateTicket(String ticketCode);
    void undoCheckIn(String checkInId);
}
