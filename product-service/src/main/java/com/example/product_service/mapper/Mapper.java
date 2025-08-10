package com.example.product_service.mapper;

import com.example.product_service.dto.ProductCreate;
import com.example.shared.ProductDTO;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.dto.ProductUpdate;
import com.example.product_service.entity.Product;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    // DTO → Entity
    Product toEntity(ProductCreate dto);

    // Entity → DTO
    ProductResponse toResponseDto(Product client);

    // Entity → DTO общий
    ProductDTO toDto(Product client);

    // Update DTO → Entity (частичное обновление)
    void updateEntityFromDto(ProductUpdate dto, @MappingTarget Product entity);
}
