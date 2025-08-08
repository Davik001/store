package com.example.employee_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    Long id;

    @Column("first_name")
    @NotBlank(message = "Имя не должно быть пустым")
 //   @JsonProperty("first_name")
    String firstName;

    @Column("last_name")
    @NotBlank(message = "Фамилия не должна быть пустой")
  //  @JsonProperty("last_name")
    String lastName;

    @Column
    @Pattern(regexp = ".*@.*", message = "Email must contain '@'")
   // @JsonProperty("email")
    String email;

    @Column
   // @JsonProperty("password")
    String password;

    @Column("role")
   // @JsonProperty("role")
    Role role;
}
