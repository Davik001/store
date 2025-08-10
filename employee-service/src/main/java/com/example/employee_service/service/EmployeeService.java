package com.example.employee_service.service;

import com.example.employee_service.Employee;
import com.example.employee_service.EmployeeMapper;
import com.example.employee_service.dto.CreateEmp;
import com.example.employee_service.dto.UpdateEmp;
import com.example.employee_service.exception.custom_exception.EmployeeAlreadyExistsException;
import com.example.employee_service.exception.custom_exception.EmployeeNotFoundException;
import com.example.employee_service.repo.EmployeeRepo;
import com.example.employee_service.dto.ResponseEmp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeMapper mapper;
    private final EmployeeRepo repo;

    @Autowired
    public EmployeeService(EmployeeMapper mapper, EmployeeRepo repo) {
        this.mapper = mapper;
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Mono<ResponseEmp> getEmployee(Long id) {
        log.info("Getting employee with id: {}", id);
        return repo.findById(id).
                doOnNext(employee -> log.debug("Employee found: {}", employee))
                .switchIfEmpty(Mono.defer(() -> {
                       log.warn("Employee with {} id not found", id);
                       return Mono.error(new EmployeeNotFoundException("Employee not found"));
                }))
                .map(mapper::toResponseEmp)
                .doOnSuccess(response -> log.info("Return employee response {}", response));
    }

    @Transactional(readOnly = true)
    public Flux<ResponseEmp> getAllEmployees() {
        log.info("Getting all employees");
        return repo.findAll()
                .doOnNext(employee -> log.debug("Found employee: {}", employee))
                .map(mapper::toResponseEmp);
    }

    public Mono<ResponseEmp> createEmployee(CreateEmp employee) {
        log.info("Creating employee: {}", employee.firstName() + " " + employee.lastName());
        return repo.existsByEmail(employee.email())
                .flatMap(exists ->{
                    if(exists) {
                        log.warn("Employee with email {} already exists", employee.email());
                        return Mono.error(new EmployeeAlreadyExistsException("Employee already exists"));
                    }

                    Employee entity = mapper.toEntity(employee);
                    log.debug("Mapped entity for creation: {}", entity);
                    return repo.save(entity)
                            .doOnSuccess(saved -> log.info("Successfully created employee with id: {}", saved.getId()))
                            .map(mapper::toResponseEmp);
                });
    }

    @Transactional
    public Mono<Void> deleteEmployee(Long id) {
        log.info("Attempting to delete employee with id: {}", id);

        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Client with id {} not found for deletion", id);
                    return Mono.error(new EmployeeNotFoundException("Employee not found"));
                })).flatMap(employee -> {
                    log.debug("Deleting employee: {}", employee);
                    return repo.deleteById(id)
                            .doOnSuccess(v -> log.info("Successfully deleted employee with id: {}", id));
                });
    }

    @Transactional
    public Mono<ResponseEmp> updateEmployee(Long id, UpdateEmp employee) {
        log.info("Attempting to update employee with id: {}", id);
        return repo.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Employee with {} id not found for update", id);
                    return Mono.error(new EmployeeNotFoundException("Employee not found"));
                }))
                .flatMap(exists -> {
                    log.debug("Existing employee before update: {}", exists);
                    mapper.updateEntityFromDto(employee, exists);
                    log.debug("Employee after update {}", exists);
                    return repo.save(exists)
                            .doOnSuccess(updated -> log.info("Successfully updated employee with id: {}", updated.getId()))
                            .map(mapper::toResponseEmp);
                });
    }
}
