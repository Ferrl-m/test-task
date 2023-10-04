package com.example.testtask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"user\"")
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email(message = "Invalid email")
    @NotNull(message = "Email must not be empty")
    private String email;
    @NotNull(message = "Last name must not be empty")
    private String lastName;
    @NotNull(message = "First name must not be empty")
    private String firstName;
    @NotNull(message = "Birthday must not be empty")
    private LocalDate birthday;
    private String address;
    private String phoneNumber;

}
