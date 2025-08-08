package com.example.client_service.mapper;

import com.example.client_service.dto.ClientDTO;
import com.example.client_service.dto.ClientResponse;
import com.example.client_service.dto.CreateClient;
import com.example.client_service.dto.UpdateClient;
import com.example.client_service.entity.Client;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    // DTO → Entity
    Client toEntity(CreateClient dto);

    // Entity → Response DTO
    ClientResponse toResponseDto(Client client);

    // Entity → ClientDTO
    ClientDTO toDto(Client client);

    // Update DTO → Entity (частичное обновление)
    void updateEntityFromDto(UpdateClient dto, @MappingTarget Client entity);
}
