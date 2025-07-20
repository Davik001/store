package com.example.MainService.map;

import com.example.MainService.dto.common.ProductResponseDTO;
import com.example.MainService.dto.create.ProductCreateDTO;
import com.example.MainService.dto.update.ProductUpdateDTO;
import com.example.MainService.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toResponseDto(Product product);
    Product toEntity(ProductCreateDTO productCreateDTO);
    Product toEntity(ProductUpdateDTO productUpdateDTO);
    List<ProductResponseDTO> toResponseDtoList(List<Product> products);
}
