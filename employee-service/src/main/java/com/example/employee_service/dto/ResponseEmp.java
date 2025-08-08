package com.example.employee_service.dto;

import com.example.employee_service.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public record ResponseEmp(
     Long id,
     String firstName,
     String lastName,
     String email,
     Role role){}

