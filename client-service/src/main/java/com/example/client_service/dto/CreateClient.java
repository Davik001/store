package com.example.client_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClient(String firstName,
                           String lastName,
                           String email,
                           String phone) {
}
