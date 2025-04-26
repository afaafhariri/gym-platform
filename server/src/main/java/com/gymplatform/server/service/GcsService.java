package com.gymplatform.server.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;

    @Value("${gcs.bucket-name}")
    private String bucketName;

    public String uploadFile(Path filePath, String folderName) throws IOException {
        String objectName = folderName + "/" + UUID.randomUUID() + "-" + filePath.getFileName();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName)
                .setContentType("media")
                .build();

        storage.create(blobInfo, java.nio.file.Files.readAllBytes(filePath));

        return "https://storage.googleapis.com/" + bucketName + "/" + objectName;
    }

    public void uploadTestFile() {
        try {
            Path tempFile = Path.of("test-upload.txt");
            java.nio.file.Files.writeString(tempFile, "Hello GCS from Spring Boot!");

            String uploadedUrl = uploadFile(tempFile, "test-folder");

            System.out.println("✅ File uploaded successfully: " + uploadedUrl);

            java.nio.file.Files.deleteIfExists(tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            storage.delete(bucketName, "posts/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}