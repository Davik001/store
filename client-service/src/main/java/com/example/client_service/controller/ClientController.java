package com.example.client_service.controller;

import com.example.client_service.dto.ClientDTO;
import com.example.client_service.dto.ClientResponse;
import com.example.client_service.dto.CreateClient;
import com.example.client_service.dto.UpdateClient;
import com.example.client_service.entity.Client;
import com.example.client_service.exception.custom_exception.ClientAlreadyExistsException;
import com.example.client_service.exception.custom_exception.ClientNotFoundException;
import com.example.client_service.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientResponse>> getClient(@PathVariable Long id) {
        log.info("Received GET /clients/{} request", id);
        return clientService.getClient(id)
                .map(ResponseEntity::ok)
                .doOnSuccess(r -> log.info("Client with id={} successfully retrieved", id))
                .onErrorResume(ClientNotFoundException.class, e -> {
                    log.warn("Client with id={} not found", id);
                    return Mono.just(ResponseEntity.notFound().build());
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClientResponse>> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClient dto) {
        log.info("Received PUT /clients/{} request with body: {}", id, dto);
        return clientService.updateClient(id, dto)
                .map(ResponseEntity::ok)
                .doOnSuccess(r -> log.info("Client with id={} successfully updated", id))
                .onErrorResume(ClientNotFoundException.class, e -> {
                    log.warn("Client with id={} not found for update", id);
                    return Mono.just(ResponseEntity.notFound().build());
                });
    }

    @PostMapping
    public Mono<ResponseEntity<ClientResponse>> createClient(@Valid @RequestBody CreateClient dto) {
        log.info("Received POST /clients request with body: {}", dto);
        return clientService.createClient(dto)
                .map(saved -> {
                    log.info("Client successfully created with id={}", saved.getId());
                    return ResponseEntity.status(HttpStatus.CREATED).body(saved); // преобразуем в ResponseEntity
                })
                .onErrorResume(ClientAlreadyExistsException.class, e -> {
                    log.warn("Client with email={} already exists", dto.email());
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable Long id) {
        log.info("Received DELETE /clients/{} request", id);
        return clientService.deleteClientById(id)
                .doOnSuccess(v -> log.info("Client with id={} successfully deleted", id))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping
    public Flux<ClientDTO> getClients() {
        log.info("Received GET /clients request");
        return clientService.getClients()
                .doOnNext(client -> log.debug("Returning client: {}", client));
    }
}

