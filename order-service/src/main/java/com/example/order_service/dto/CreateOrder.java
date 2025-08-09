package com.example.order_service.dto;

import com.example.order_service.OrderStatus;

import java.util.List;

public record CreateOrder(
     Long clientId,
     OrderStatus status,
     List<OrderItemDto> items){
}
