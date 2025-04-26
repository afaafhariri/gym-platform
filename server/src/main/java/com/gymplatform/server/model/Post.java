package com.gymplatform.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    private String userId;
    private List<String> mediaUrls;
    private String description;
    private String mediaType;
    private Instant createdAt;
    private Instant updatedAt;
}