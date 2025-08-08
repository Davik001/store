package com.example.employee_service.repo;

import com.example.employee_service.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EmployeeRepo extends ReactiveCrudRepository<Employee, Long> {
    public Mono<Boolean> existsByEmail(String email);
}
