package com.example.order_service.dto;

import com.example.order_service.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto (
     Long id,
     Long clientId,
     LocalDateTime createdAt,
     OrderStatus status,
     List<OrderItemDto> items){
}
