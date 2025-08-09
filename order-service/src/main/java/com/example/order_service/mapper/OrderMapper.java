package com.example.order_service.mapper;

import com.example.order_service.dto.CreateOrder;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderItemDto;
import com.example.order_service.dto.UpdateOrder;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDto dto);

    List<OrderItem> toEntityList(List<OrderItemDto> dto);

    @Mapping(target = "items", source = "items")
    Order toEntity(CreateOrder dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    void updateEntityFromDto(UpdateOrder dto, @MappingTarget Order order);
}
