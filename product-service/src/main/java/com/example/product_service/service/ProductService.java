package com.example.product_service.service;

import com.example.product_service.dto.ProductCreate;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.dto.ProductUpdate;
import com.example.product_service.entity.Product;
import com.example.product_service.exceptions.custom_exception.ProductNotExistsException;
import com.example.product_service.mapper.Mapper;
import com.example.product_service.repo.PorductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductService {

    PorductRepo repo;
    Mapper mapper;

    @Autowired
    public ProductService(PorductRepo repo, Mapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Mono<ProductResponse> getProduct(Long id) {
        log.info("Get product with id {}", id);

        return repo.findById(id)
                .doOnNext(product -> log.debug("Product {} found", product))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client with id {} not found for deletion", id);
                    return Mono.error(new ProductNotExistsException("Product not found"));
                }))
                .map(mapper::toResponseDto)
                .doOnSuccess(productResponse -> log.info("Return product {}", productResponse));
    }

    public Flux<ProductResponse> getAllProducts() {
        log.info("get All Products called");

        return repo.findAll()
                .doOnNext(product -> log.debug("Found product {}", product))
                .map(mapper::toResponseDto);
    }

    public Mono<ProductResponse> createProduct(ProductCreate productCreate) {
        log.info("Create product {}", productCreate);

        Product entity = mapper.toEntity(productCreate);
        log.debug("Mapping entity for creation {}", entity);

        return repo.save(entity)
                .doOnSuccess(product -> log.info("Product created {}", product))
                .map(mapper::toResponseDto);
    }

    public Mono<ProductResponse> updateProduct(Long id, ProductUpdate productUpdate) {
        log.info("Update product with id {}", id);

        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client with id {} not found for deletion", id);
                    return Mono.error(new ProductNotExistsException("Product not found"));
                }))
                .flatMap(product -> {
                    log.debug("Product before update {}", product);

                    mapper.updateEntityFromDto(productUpdate, product);
                    log.debug("Product after update {}", product);

                    return repo.save(product)
                            .doOnSuccess(p -> log.info("Product with id {} updated", product.getId()))
                            .map(mapper::toResponseDto);
                });
    }

    public Mono<Void> deleteProduct(Long id){
        log.info("Deleting product with id {}", id);

        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Product with {} id not found!", id);
                    return Mono.error(new ProductNotExistsException("Product with id " + id + " not found!"));
                }))
                .flatMap(product -> {
                    log.debug("Deleting product with id {}", id);
                    return repo.deleteById(id)
                            .doOnSuccess(v -> log.info("Deleted product with id {}", id));
                });
    }

}
