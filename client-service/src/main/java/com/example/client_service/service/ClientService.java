package com.example.client_service.service;

import com.example.client_service.mapper.Mapper;
import com.example.client_service.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.client_service.entity.Client;

@Service
public class ClientService {
    Mapper mapper;
    ClientRepo repo;

    @Autowired
    public ClientService(Mapper mapper, ClientRepo clientRepo) {
        this.mapper = mapper;
        this.repo = clientRepo;
    }

    // CRUD

    public Mono<Client> getClient(Long id) {
        return repo.findById(id);
    }

    public Flux<Client> getClients() {
        return repo.findAll();
    }

    public Mono<Void> deleteClientById(Long id) {
        return repo.deleteById(id);
    }

    public Mono<Client> createClient(Client client) {
        return repo.save(client);
    }

    public Mono<Client> updateClient(Long id, Client updatedClient) {
        return repo.findById(id)
                .flatMap(existingClient -> {

                    existingClient.setFirstName(updatedClient.getFirstName());
                    existingClient.setLastName(updatedClient.getLastName());
                    existingClient.setEmail(updatedClient.getEmail());
                    existingClient.setPhone(updatedClient.getPhone());

                    return repo.save(existingClient);
                })
        .switchIfEmpty(Mono.error(new RuntimeException("Client not found")));
    }


}
