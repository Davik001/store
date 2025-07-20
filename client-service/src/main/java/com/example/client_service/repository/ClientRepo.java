package com.example.client_service.repository;

import com.example.client_service.entity.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

// ReactiveCrudRepository - для использования WebFlux
public interface ClientRepo extends ReactiveCrudRepository<Client, Long> {
}
