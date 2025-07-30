package com.example.client_service.entity;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "client")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    Long id;

    @Column("first_name")
    String firstName;

    @Column("last_name")
    String lastName;

    @Column
    @Pattern(regexp = ".*@.*", message = "Email must contain '@'")
    String email;

    @Column
    String phone;


}
