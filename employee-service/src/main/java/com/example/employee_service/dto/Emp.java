package com.example.employee_service.dto;

import com.example.employee_service.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;
}
