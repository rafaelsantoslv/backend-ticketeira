package com.unyx.ticketeira.application.usecases.batch;

import com.unyx.ticketeira.application.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.application.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.domain.model.Batch;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Sector;
import com.unyx.ticketeira.domain.repository.BatchRepository;
import com.unyx.ticketeira.domain.util.AuthorizationValidator;
import com.unyx.ticketeira.domain.util.ConvertDTO;
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
        Sector sectorExists = authorizationValidator.validateEventSector(eventId, sectorId);

        Batch newBatch = ConvertDTO.convertBatchToModel(dto);
        newBatch.setSector(sectorExists);

        Batch savedBatch = batchRepository.save(newBatch);

        return ConvertDTO.convertBatchToDto(savedBatch);
    }
}
