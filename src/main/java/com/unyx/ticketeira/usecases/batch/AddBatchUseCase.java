package com.unyx.ticketeira.usecases.batch;

import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.model.Batch;
import com.unyx.ticketeira.model.Sector;
import com.unyx.ticketeira.repository.BatchRepository;
import com.unyx.ticketeira.util.AuthorizationValidator;
import com.unyx.ticketeira.util.ConvertDTO;
import org.springframework.stereotype.Service;

@Service
public class AddBatchUseCase {
    private final BatchRepository batchRepository;
    private final AuthorizationValidator authorizationValidator;

    public AddBatchUseCase(AuthorizationValidator authorizationValidator, BatchRepository batchRepository) {
        this.authorizationValidator = authorizationValidator;
        this.batchRepository = batchRepository;
    }

    public BatchCreateResponse execute(String eventId, String sectorId, String userId, BatchCreateRequest dto){
        authorizationValidator.validateEventProducer(eventId, userId);
        Sector sectorExists = authorizationValidator.validateEventSector(eventId, sectorId);

        Batch newBatch = ConvertDTO.convertBatchToModel(dto);
        newBatch.setSector(sectorExists);

        Batch savedBatch = batchRepository.save(newBatch);

        return ConvertDTO.convertBatchToDto(savedBatch);
    }
}
