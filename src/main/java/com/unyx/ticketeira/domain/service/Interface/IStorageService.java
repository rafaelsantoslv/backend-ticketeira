package com.unyx.ticketeira.domain.service.Interface;

public interface IStorageService {
    String generateUploadUrl(String objectKey);
    String getPublicUrl(String objectKey);
}
