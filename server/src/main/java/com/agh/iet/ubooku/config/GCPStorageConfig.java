package com.agh.iet.ubooku.config;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

@Configuration
public class GCPStorageConfig {

    public static final String BUCKET_BOOKS = "ubooku-books-bucket";
    private static final String PROJECT_ID = "ubookucloud";

    private static final Logger logger = LoggerFactory.getLogger(GCPStorageConfig.class);



    @Bean
    public Storage storage() throws IOException {

        String credentialsPath = Stream.of("main", "resources", "ubookuCloud-99311afebc4d.json").reduce(
                "src", (p, s) -> p + File.separator + s);

        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(credentialsPath));

        return StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(PROJECT_ID).build().getService();
    }

}
