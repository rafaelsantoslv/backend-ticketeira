package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.model.UploadInfo;

public interface IStorageService {
    UploadInfo generateUploadUrl();
    String getPublicUrl(String objectKey);
}
