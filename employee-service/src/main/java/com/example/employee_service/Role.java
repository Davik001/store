package com.example.employee_service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Role {
    ADMIN("Админ"),
    MANAGER("Менеджер");

    private final String role;

    Role(String role) {
        this.role = name();
    }
}
