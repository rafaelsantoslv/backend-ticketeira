package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.Batch;

import java.util.List;
import java.util.Optional;

public interface IBatchService {
    Batch create(Batch batch);
    Batch update(String id, Batch batch);
    void delete(String id);
    Optional<Batch> findById(String id);
    List<Batch> findByEvent(String eventId);
    Batch activate(String id);
    Batch deactivate(String id);
    boolean isAvailable(String id);
    Batch updateQuantity(String id, Integer quantity);
    Optional<Batch> getCurrentBatch(String eventId);
}
