package com.example.employee_service.controller;

import com.example.employee_service.Employee;
import com.example.employee_service.dto.CreateEmp;
import com.example.employee_service.dto.ResponseEmp;
import com.example.employee_service.dto.UpdateEmp;
import com.example.employee_service.exception.custom_exception.EmployeeAlreadyExistsException;
import com.example.employee_service.exception.custom_exception.EmployeeNotFoundException;
import com.example.employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Mono<ResponseEntity<ResponseEmp>> createEmployee(@Valid @RequestBody CreateEmp createEmp) {
        log.info("Received POST /employees request with body: {}", createEmp);

        return employeeService.createEmployee(createEmp)
                .map(saveEmployee -> {
                    log.info("Employee successfully created with id={}", saveEmployee.getId());
                    return ResponseEntity.status(HttpStatus.CREATED).body(saveEmployee);
                })
                .onErrorResume(EmployeeAlreadyExistsException.class, e -> {
                    log.warn("Employee with email {} already exists!", createEmp.email());
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
                });
        // onErrorResume позволяет обработать произошедшую в цепочке ошибку и перехватывает ее, как try/catch
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ResponseEmp>> getEmployee(@PathVariable Long id) {
        log.info("Received GET /employees with id={}", id);

        return employeeService.getEmployee(id)
                .map(ResponseEntity::ok)
                .doOnSuccess(r -> log.info("Employee with id={} successfully retrieved", id))
                .onErrorResume(EmployeeNotFoundException.class, e -> {
                    log.warn("Employee with id={} not found!", id);
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                });
    }

    @GetMapping
    public Flux<ResponseEmp> getAllEmployees() {
        log.info("Received GET /employees");

        return employeeService.getAllEmployees()
                .doOnNext(r -> log.info("Employee with id={} successfully retrieved", r.getId()));
    }

    @PutMapping("/id")
    public Mono<ResponseEntity<ResponseEmp>> updateEmployee(@PathVariable Long id,
                                                            @Valid @RequestBody UpdateEmp updateEmp) {
        log.info("Received PUT /employees/{} request with body: {}", id, updateEmp);

        return employeeService.updateEmployee(id, updateEmp)
                .map(ResponseEntity::ok)
                .doOnSuccess(r -> log.info("Employee with id={} successfully updated", id))
                .onErrorResume(EmployeeNotFoundException.class, e -> {
                    log.warn("Employee with id={} not found!", id);
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                });

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable Long id){
        log.info("Received DELETE /employees with id={}", id);

        return employeeService.deleteEmployee(id)
                .doOnSuccess(r -> log.info("Employee with id {} deleted", id))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
