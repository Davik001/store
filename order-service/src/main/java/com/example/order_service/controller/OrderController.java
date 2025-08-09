package com.example.order_service.controller;

import com.example.order_service.dto.CreateOrder;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.UpdateOrder;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrder dto) {
        log.info("Создание заказа: {}", dto);
        OrderDto createdOrder = service.createOrder(dto);
        log.info("Заказ создан: {}", createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
        log.info("Получение заказа с id={}", id);
        OrderDto order = service.getOrder(id);
        log.info("Заказ найден: {}", order);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.info("Получение списка всех заказов");
        List<OrderDto> orders = service.getAllOrders();
        log.info("Найдено {} заказов", orders.size());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id,
                                                @RequestBody UpdateOrder dto) {
        log.info("Обновление заказа с id={}: {}", id, dto);
        OrderDto updatedOrder = service.updateOrder(id, dto);
        log.info("Заказ обновлён: {}", updatedOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("Удаление заказа с id={}", id);
        service.deleteOrder(id);
        log.info("Заказ с id={} удалён", id);
        return ResponseEntity.noContent().build();
    }
}
