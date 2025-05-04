package com.unyx.ticketeira.application.usecases.batch;

import com.unyx.ticketeira.application.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.domain.model.Batch;
import com.unyx.ticketeira.domain.repository.BatchRepository;
import com.unyx.ticketeira.domain.util.AuthorizationValidator;
import com.unyx.ticketeira.domain.util.ConvertDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBatchesUseCase {
    private final BatchRepository batchRepository;
    private final AuthorizationValidator authorizationValidator;

    public GetBatchesUseCase(AuthorizationValidator authorizationValidator, BatchRepository batchRepository) {
        this.authorizationValidator = authorizationValidator;
        this.batchRepository = batchRepository;
    }

    public List<BatchListAllBySector> execute(String eventId, String sectorId, String userId){
        authorizationValidator.validateEventProducer(eventId, userId);
        List<Batch> batchList = batchRepository.findAllBySectorId(sectorId);
        return batchList.stream().map(ConvertDTO::convertBatchListToDto).toList();
    }
}
