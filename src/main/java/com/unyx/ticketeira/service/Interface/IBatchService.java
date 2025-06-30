package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.model.Batch;

import java.util.List;

public interface IBatchService {
    BatchCreateResponse createBatch(String eventId, String sectorId, String userId, BatchCreateRequest dto);
    List<BatchListAllBySector> listAllBatchByEvent(String eventId, String sectorId, String userId);

    Batch validateBatchAndGetBatch(String batchId);
}
