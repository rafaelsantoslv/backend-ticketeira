package com.unyx.ticketeira.service;

import com.unyx.ticketeira.model.Courtesy;
import com.unyx.ticketeira.repository.CourtesyRepository;
import com.unyx.ticketeira.service.Interface.ICourtesyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CourtesyService implements ICourtesyService {
    private final CourtesyRepository courtesyRepository;

    public List<Courtesy> getCourtesiesByEventId(String eventId) {
        return courtesyRepository.findCourtesiesByEventId(eventId);
    }
}
