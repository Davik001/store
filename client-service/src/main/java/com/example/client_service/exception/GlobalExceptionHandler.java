package com.example.client_service.exception;

import com.example.client_service.exception.custom_exception.ClientAlreadyExistsException;
import com.example.client_service.exception.custom_exception.ClientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public Mono<ResponseEntity<String>> handleClientNotFound(ClientNotFoundException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.TEXT_PLAIN) // чтобы фронтенд или клиент поняли
                        .body(ex.getMessage())
        );
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public Mono<ResponseEntity<String>> handleClientAlreadyExists(ClientAlreadyExistsException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.CONFLICT)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(ex.getMessage())
        );
    }

}
