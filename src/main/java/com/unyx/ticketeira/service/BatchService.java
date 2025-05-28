package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.model.Batch;
import com.unyx.ticketeira.model.Sector;
import com.unyx.ticketeira.repository.BatchRepository;
import com.unyx.ticketeira.service.Interface.IBatchService;
import com.unyx.ticketeira.util.AuthorizationValidator;
import com.unyx.ticketeira.util.ConvertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService implements IBatchService {
    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private AuthorizationValidator authorizationValidator;

    public BatchCreateResponse createBatch(String eventId, String sectorId, String userId, BatchCreateRequest dto) {
        authorizationValidator.validateEventProducer(eventId, userId);
        Sector sectorExists = authorizationValidator.validateEventSector(eventId, sectorId);

        Batch newBatch = ConvertDTO.convertBatchToModel(dto);
        newBatch.setSector(sectorExists);

        Batch savedBatch = batchRepository.save(newBatch);

        return ConvertDTO.convertBatchToDto(savedBatch);
    }

    public List<BatchListAllBySector> listAllBatchByEvent(String eventId, String sectorId, String userId) {
        authorizationValidator.validateEventProducer(eventId, userId);
        List<Batch> batchList = batchRepository.findAllBySectorId(sectorId);
        return batchList.stream().map(ConvertDTO::convertBatchListToDto).toList();
    }
}
