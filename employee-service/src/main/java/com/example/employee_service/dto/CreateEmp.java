package com.example.employee_service.dto;

import com.example.employee_service.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateEmp(@NotBlank String firstName,
                        @NotBlank String lastName,
                        @Email String email,
                        String password,
                        Role role) {
}
