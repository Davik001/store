package com.example.client_service.repository;

import com.example.client_service.entity.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

// ReactiveCrudRepository - для использования WebFlux
@Repository
public interface ClientRepo extends ReactiveCrudRepository<Client, Long> {
    public Mono<Client> findByEmail(String email);
    public Mono<Boolean> existsByEmail(String email);
}
