package com.findar.bookkeeper.repositories;

import com.findar.bookkeeper.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long>{
    Mono<User> findUserByEmailIgnoreCase(String email);
    Mono<Boolean> existsByEmailIgnoreCase(String email);
}
