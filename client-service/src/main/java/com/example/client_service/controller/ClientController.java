package com.example.client_service.controller;

import com.example.client_service.entity.Client;
import com.example.client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    // Получить клиента по id
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> getClient(@PathVariable Long id) {
        return clientService.getClient(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Обновить клиента по id (PUT)
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> updateClient(
            @PathVariable Long id,
            @RequestBody Client updatedClient) {

        return clientService.updateClient(id, updatedClient)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Создать нового клиента (POST)
    @PostMapping
    public Mono<Client> createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    // Удалить клиента по id
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable Long id) {
        return clientService.deleteClientById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    // Получить всех клиентов
    @GetMapping
    public Flux<Client> getClients() {
        return clientService.getClients();
    }

}
