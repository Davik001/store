package com.example.order_service.dto;

import com.example.order_service.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrder {
    private OrderStatus status;
    private List<OrderItemDto> items;
}