package com.example.webfluxwithmongoexample.infrastructure.persistence.mongo;

import com.example.webfluxwithmongoexample.domain.user.User;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserMongoRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    public UserMongoRepository(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<User> save(User user) {
        return mongoTemplate.save(user);
    }
}
