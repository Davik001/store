package com.example.employee_service.dto;

import com.example.employee_service.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmp {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;
}
