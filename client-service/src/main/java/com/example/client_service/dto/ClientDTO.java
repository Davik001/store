package com.example.client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// общий дто, для обмена всеми данными
public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
