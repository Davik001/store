package com.example.MainService.map;


import com.example.MainService.dto.common.OrderResponseDTO;
import com.example.MainService.dto.create.OrderCreateDTO;
import com.example.MainService.dto.update.OrderUpdateDTO;
import com.example.MainService.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customer", ignore = true)
    Order toEntity(OrderCreateDTO dto);

    @Mapping(target = "customer", ignore = true)
    Order toEntity(OrderUpdateDTO dto);

   // @Mapping(target = "customer", ignore = true)
    OrderResponseDTO toDTO(Order order);
}
