package com.example.order_service.service;

import com.example.order_service.dto.CreateOrder;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.UpdateOrder;
import com.example.order_service.entity.Order;
import com.example.order_service.exception.custom_exceptions.OrderNotFoundException;
import com.example.order_service.mapper.OrderMapper;
import com.example.order_service.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final WebClient webClient;

    public OrderDto createOrder(CreateOrder dto) {
        log.info("Creating new order: {}", dto);
        Order order = mapper.toEntity(dto);

        // Связываем order с OrderItem вручную, т.к. MapStruct не умеет это делать автоматически
        order.getItems().forEach(item -> item.setOrder(order));

        Order saved = repo.save(order);
        log.info("Order created successfully: {}", saved);
        return mapper.toDto(saved);
    }

    public OrderDto getOrder(Long id) {
        log.info("Fetching order with id: {}", id);
        Order order = repo.findByIdWithItems(id)
                .orElseThrow(() -> {
                    log.warn("Order with id {} not found", id);
                    return new OrderNotFoundException("Order not found");
                });
        log.info("Order fetched: {}", order);
        return mapper.toDto(order);
    }

    public List<OrderDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = repo.findAllWithItems();
        log.info("Fetched {} orders", orders.size());
        return mapper.toDtoList(orders);
    }

    public OrderDto updateOrder(Long id, UpdateOrder dto) {
        log.info("Updating order with id {}: {}", id, dto);
        Order order = repo.findByIdWithItems(id)
                .orElseThrow(() -> {
                    log.warn("Order with id {} not found for update", id);
                    return new OrderNotFoundException("Order not found");
                });

        mapper.updateEntityFromDto(dto, order);

        // Связываем order с OrderItem (в случае замены списка)
        order.getItems().forEach(item -> item.setOrder(order));

        Order saved = repo.save(order);
        log.info("Order with id {} updated successfully: {}", id, saved);
        return mapper.toDto(saved);
    }

    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        repo.deleteById(id);
        log.info("Order with id {} deleted successfully", id);
    }
}

