package com.unyx.ticketeira.mapper;

import com.unyx.ticketeira.dto.batch.BatchDTO;
import com.unyx.ticketeira.model.Batch;

public class BatchMapper {
    public static BatchDTO toDTO(Batch batch, long sold) {
            return new BatchDTO(
                     batch.getId(),
                     batch.getName(),
                     batch.getPrice(),
                     batch.getQuantity(),
                     sold,
                     batch.getIsActive()
            );
      }
    }

