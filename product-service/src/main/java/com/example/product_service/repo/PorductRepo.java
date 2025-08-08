package com.example.product_service.repo;

import com.example.product_service.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorductRepo extends ReactiveCrudRepository<Product, Long> {
}
