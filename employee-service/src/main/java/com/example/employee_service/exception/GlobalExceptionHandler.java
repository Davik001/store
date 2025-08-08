package com.example.employee_service.exception;

import com.example.employee_service.exception.custom_exception.EmployeeAlreadyExistsException;
import com.example.employee_service.exception.custom_exception.EmployeeNotFoundException;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public Mono<ResponseEntity<String>> handlerEmployeeNotFound(EmployeeNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN)
                .body(ex.getMessage())
        );
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public Mono<ResponseEntity<String>> handlerEmployeeExists(EmployeeAlreadyExistsException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.TEXT_PLAIN)
                .body(ex.getMessage())
        );
    }
}
