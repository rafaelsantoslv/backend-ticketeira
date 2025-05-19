package com.unyx.ticketeira.service;

import com.unyx.ticketeira.model.UploadInfo;
import com.unyx.ticketeira.service.Interface.IStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudflareStorageService implements IStorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    @Value("${cloudflare.r2.bucket.public}")
    private String bucketPublic;


    @Override
    public UploadInfo generateUploadUrl() {

        String objectKey = generateObjectKey();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();

        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignRequest);
        return new UploadInfo(objectKey, presigned.url().toString());
    }

    @Override
    public String getPublicUrl(String objectKey) {
        return String.format("https://%s/%s", bucketPublic, objectKey);
    }

    private String generateObjectKey() {
        String uuid = UUID.randomUUID().toString();

        // Gera um timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Combina tudo em um path organizado
        return String.format("events/%s/%s", timestamp, uuid);
    }
}
