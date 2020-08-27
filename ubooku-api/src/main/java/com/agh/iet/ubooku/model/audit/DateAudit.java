package com.agh.iet.ubooku.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;


public abstract class DateAudit implements Serializable {

    @Getter @Setter
    private Instant createdAt;

    @Getter @Setter
    private Instant updatedAt;

}
