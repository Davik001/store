package com.example.client_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClient(@JsonProperty("first_name")String firstname,
                           @JsonProperty("last_name")String lastname, String email, String phone) {
}
