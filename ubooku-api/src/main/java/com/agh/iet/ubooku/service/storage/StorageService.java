package com.agh.iet.ubooku.service.storage;

import com.google.cloud.storage.BlobId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    BlobId store(MultipartFile file, String id) throws IOException;

    BlobId store(byte[] bytes, String id, String contentType);

    byte[] load(BlobId blobId);

    void delete(BlobId blobId);

}
