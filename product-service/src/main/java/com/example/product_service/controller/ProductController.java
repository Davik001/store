package com.example.product_service.controller;

import com.example.product_service.dto.ProductCreate;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.dto.ProductUpdate;
import com.example.product_service.exceptions.custom_exception.ProductNotExistsException;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Mono<ResponseEntity<ProductResponse>> createProduct(@Valid @RequestBody ProductCreate productCreate) {
        log.info("Received POST /products request with body: {}", productCreate);

        return productService.createProduct(productCreate)
                .map(productResponse -> {
                    log.info("Received POST /products response with body: {}", productResponse);
                    return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductResponse>> updateProduct(@PathVariable Long id,
                                                               @Valid @RequestBody ProductUpdate productUpdate) {
        log.info("Received PUT /products request with body: {}", productUpdate);

        return productService.updateProduct(id, productUpdate)
                .map(ResponseEntity::ok)
                .doOnSuccess(productResponse -> log.info("Received PUT /products response with body: {}", productResponse))
                .onErrorResume(t -> {
                    log.warn("Product with id {} not found!", id);
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Long id) {
        log.info("Received DELETE /products request with body: {}", id);

        return productService.deleteProduct(id)
                .doOnSuccess(v -> log.info("Deleted product with id {}", id))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductResponse>> getProduct(@PathVariable Long id) {
        log.info("Received GET /products request with body: {}", id);

        return productService.getProduct(id)
                .map(ResponseEntity::ok)
                .doOnSuccess(productResponse -> log.info("Product with id {} found", id))
                .onErrorResume(ProductNotExistsException.class, e -> {
                    log.warn("Product with id {} not found!", id);
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                });
    }

    @GetMapping
    public Flux<ProductResponse> getAllProducts() {
        log.info("Received GET /products");

        return productService.getAllProducts()
                .doOnNext(productResponse -> log.info("Product with id {}", productResponse.id()));
    }
}
