package com.gymplatform.server.repository;

import com.gymplatform.server.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByUserId(String userId);
}