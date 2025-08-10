package com.example.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


public record ProductDTO(
     Long id,
     String name,
     String description,
     BigDecimal price){
}
