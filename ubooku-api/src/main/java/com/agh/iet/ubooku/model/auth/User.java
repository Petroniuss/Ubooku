package com.agh.iet.ubooku.model.auth;

import com.agh.iet.ubooku.model.audit.DateAudit;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
@Data
@Builder
public class User extends DateAudit{

    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Email
    private String email;

    @JsonIgnore
    private String password;

    private String imageUrl;

    private String providerId;

    private AuthProvider provider;

    private Set<Role> roles;

}
