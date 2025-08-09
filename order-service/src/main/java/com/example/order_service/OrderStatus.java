package com.example.order_service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderStatus {
    NEW,
    PROCESSING,
    COMPLETED,
    CANCELED
}
