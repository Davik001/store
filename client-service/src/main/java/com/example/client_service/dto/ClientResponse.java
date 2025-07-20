package com.example.client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;

    public ClientResponse(Long id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.phone = phone;
    }
}
