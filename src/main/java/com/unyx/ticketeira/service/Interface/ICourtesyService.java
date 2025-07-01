package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.model.Courtesy;

import java.util.List;

public interface ICourtesyService {
    List<Courtesy> getCourtesiesByEventId(String eventId);
}
