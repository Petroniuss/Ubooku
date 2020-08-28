package com.agh.iet.ubooku.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.storage.BlobId;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@QueryEntity
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private String author;

    private List<Categories> categories;

    @JsonIgnore
    private BlobId blobId;

    @JsonIgnore
    private BlobId thumbnailBlobId;
}
