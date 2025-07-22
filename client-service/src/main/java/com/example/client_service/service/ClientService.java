package com.example.client_service.service;

import com.example.client_service.dto.ClientDTO;
import com.example.client_service.dto.ClientResponse;
import com.example.client_service.dto.CreateClient;
import com.example.client_service.dto.UpdateClient;
import com.example.client_service.exception.custom_exception.ClientAlreadyExistsException;
import com.example.client_service.exception.custom_exception.ClientNotFoundException;
import com.example.client_service.mapper.Mapper;
import com.example.client_service.repository.ClientRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.client_service.entity.Client;

@Service
@Slf4j
public class ClientService {
    private final Mapper mapper;
    private final ClientRepo repo;

    @Autowired
    public ClientService(Mapper mapper, ClientRepo repo) {
        this.mapper = mapper;
        this.repo = repo;
    }

    public Mono<ClientResponse> getClient(Long id) {
        log.info("Fetching client with id: {}", id);
        return repo.findById(id)
                .doOnNext(client -> log.debug("Client found: {}", client))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client with id {} not found", id);
                    return Mono.error(new ClientNotFoundException("Client not found"));
                }))
                .map(mapper::toResponseDto)
                .doOnSuccess(response -> log.info("Returning client response: {}", response));
    }

    public Flux<ClientDTO> getClients() {
        log.info("Fetching all clients");
        return repo.findAll()
                .doOnNext(client -> log.debug("Found client: {}", client))
                .map(mapper::toDto);
    }

    public Mono<Void> deleteClientById(Long id) {
        log.info("Attempting to delete client with id: {}", id);
        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client with id {} not found for deletion", id);
                    return Mono.error(new ClientNotFoundException("Client not found"));
                }))
                .flatMap(client -> {
                    log.debug("Deleting client: {}", client);
                    return repo.deleteById(id)
                            .doOnSuccess(v -> log.info("Successfully deleted client with id: {}", id));
                });
    }

    public Mono<ClientResponse> createClient(CreateClient dto) {
        log.info("Creating client with email: {}", dto.email());
        return repo.existsByEmail(dto.email())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Client with email {} already exists", dto.email());
                        return Mono.error(new ClientAlreadyExistsException(
                                "Client with email " + dto.email() + " already exists"));
                    }

                    Client entity = mapper.toEntity(dto);
                    log.debug("Mapped entity for creation: {}", entity);
                    return repo.save(entity)
                            .doOnSuccess(saved -> log.info("Successfully created client with id: {}", saved.getId()))
                            .map(mapper::toResponseDto);
                });
    }

    public Mono<ClientResponse> updateClient(Long id, UpdateClient dto) {
        log.info("Updating client with id: {}", id);
        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client with id {} not found for update", id);
                    return Mono.error(new ClientNotFoundException("Client not found"));
                }))
                .flatMap(existing -> {
                    log.debug("Existing client before update: {}", existing);
                    mapper.updateEntityFromDto(dto, existing);
                    log.debug("Client after applying updates: {}", existing);
                    return repo.save(existing)
                            .doOnSuccess(updated -> log.info("Successfully updated client with id: {}", updated.getId()))
                            .map(mapper::toResponseDto);
                });
    }
}


