package com.agh.iet.ubooku;

import com.agh.iet.ubooku.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class UBookuApplication {

    public static void main(String[] args) {
        SpringApplication.run(UBookuApplication.class, args);
    }


}
