package com.example.order_service.service;

import com.example.order_service.dto.CreateOrder;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.UpdateOrder;
import com.example.order_service.entity.Order;
import com.example.order_service.exception.custom_exceptions.OrderNotFoundException;
import com.example.order_service.mapper.OrderMapper;
import com.example.order_service.repo.OrderRepository;
import com.example.shared.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final WebClient productClient;

    public OrderDto createOrder(CreateOrder dto) {
        log.info("Creating new order: {}", dto);

        Order order = mapper.toEntity(dto);

        // Связь item → order + валидация productId через product-service
        order.getItems().forEach(item -> {
            item.setOrder(order);
            ProductDTO product = fetchProductOrThrow(item.getProductId());
            log.debug("Validated product id={}, name={}, price={}", product.id(), product.name(), product.price());
        });

        Order saved = repo.save(order);
        log.info("Order created successfully: id={}", saved.getId());
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public OrderDto getOrder(Long id) {
        log.info("Fetching order with id: {}", id);
        Order order = repo.findByIdWithItems(id)
                .orElseThrow(() -> {
                    log.warn("Order with id {} not found", id);
                    return new OrderNotFoundException("Order not found");
                });

        log.info("Order fetched: id={}, items={}", order.getId(), order.getItems().size());
        return mapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = repo.findAllWithItems();
        log.info("Fetched {} orders", orders.size());
        return mapper.toDtoList(orders);
    }

    @Transactional
    public OrderDto updateOrder(Long id, UpdateOrder dto) {
        log.info("Updating order with id {}: {}", id, dto);

        Order order = repo.findByIdWithItems(id)
                .orElseThrow(() -> {
                    log.warn("Order with id {} not found for update", id);
                    return new OrderNotFoundException("Order not found");
                });

        // Обновляем поля заказа и его items
        mapper.updateEntityFromDto(dto, order);

        // Перепривязываем items к order и валидируем productId
        order.getItems().forEach(item -> {
            item.setOrder(order);
            ProductDTO product = fetchProductOrThrow(item.getProductId());
            log.debug("Validated product id={}, name={}, price={}", product.id(), product.name(), product.price());
        });

        Order saved = repo.save(order);
        log.info("Order with id {} updated successfully", id);
        return mapper.toDto(saved);
    }

    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        repo.deleteById(id);
        log.info("Order with id {} deleted successfully", id);
    }

    private ProductDTO fetchProductOrThrow(Long productId) {
        try {
            return productClient
                    .get()
                    .uri("/{id}", productId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, resp ->
                            resp.createException().map(ex -> new RuntimeException(
                                    "Product with id " + productId + " not found")))
                    .bodyToMono(ProductDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            // Любые сетевые/5xx/прочие ошибки — пробрасываем как RuntimeException
            throw new RuntimeException("Failed to fetch product " + productId + ": " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product " + productId + ": " + e.getMessage(), e);
        }
    }
}

