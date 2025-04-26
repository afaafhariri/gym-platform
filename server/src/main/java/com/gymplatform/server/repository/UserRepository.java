package com.gymplatform.server.repository;

import com.gymplatform.server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByOauthIdAndProvider(String oauthId, String provider);
}
