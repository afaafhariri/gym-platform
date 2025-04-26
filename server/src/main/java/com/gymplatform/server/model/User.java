package com.gymplatform.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String oauthId;       // Google or GitHub unique ID
    private String provider;      // "Google" or "GitHub"
    private String name;
    private String email;
    private String profilePicture;
}
