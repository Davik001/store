package com.example.product_service.exceptions;

import com.example.product_service.exceptions.custom_exception.ProductNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ProductNotExistsException.class)
    public Mono<ResponseEntity<String>> handlerException(ProductNotExistsException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN)
                .body(ex.getMessage())
        );
    }
}
