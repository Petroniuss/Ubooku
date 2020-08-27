package com.agh.iet.ubooku.service;

import com.agh.iet.ubooku.config.GCPStorageConfig;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StorageService implements com.agh.iet.ubooku.service.storage.StorageService {

    public static final String MIME_APPLICATION_PDF = "application/pdf";
    public static final String MIME_IMAGE_PNG = "image/png";


    private final Storage storage;
    private final Bucket bucket;

    @Autowired
    public StorageService(Storage storage) {
        this.storage = storage;
        this.bucket = storage.get(GCPStorageConfig.BUCKET_BOOKS);
    }

    @Override
    public BlobId store(MultipartFile file, String id) throws IOException {
        byte[] bytes = file.getBytes();
        String contentType = file.getContentType();

        return store(bytes, id, contentType);
    }

    @Override
    public BlobId store(byte[] bytes, String id, String contentType) {

        if (bytes.length == 0) {
            throw new EmptyFileException();
        }

        if (contentType == null || contentType.isBlank() || contentType.isEmpty()) {
            throw new NoContentTypeException();
        }

        return bucket.create(id, bytes, contentType).getBlobId();
    }

    @Override
    public byte[] load(BlobId blobId) {

        byte[] blob = storage.get(blobId).getContent();
        if (blob == null) {
            throw new NotFoundBlobWithGivenIdException(blobId);
        }
        return blob;
    }


    @Override
    public void delete(BlobId blobId) {
        storage.delete(blobId);
    }

    private static class EmptyFileException extends RuntimeException {
        EmptyFileException() {
            super("Cannot save empty file");
        }
    }

    private static class NoContentTypeException extends RuntimeException {
        NoContentTypeException() {
            super("Specify content type!");
        }
    }

    private static class NotFoundBlobWithGivenIdException extends RuntimeException {
         NotFoundBlobWithGivenIdException(BlobId id) {
            super("Could not find blob with given blob id: " + id.getName());
        }
    }

}
