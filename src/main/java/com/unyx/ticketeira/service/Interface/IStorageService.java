package com.unyx.ticketeira.service.Interface;

public interface IStorageService {
    String generateUploadUrl(String objectKey);
    String getPublicUrl(String objectKey);
}
