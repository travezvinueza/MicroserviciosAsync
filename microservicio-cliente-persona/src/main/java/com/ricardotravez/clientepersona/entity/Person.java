package com.ricardotravez.clientepersona.entity;

import com.ricardotravez.clientepersona.dto.enums.GenderPerson;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_person")
    private GenderPerson genderPerson;
    private LocalDate date;
    private int age;
    @Column(name = "identification_number", unique = true)
    private String identificationNumber;
    private String address;
    private String phone;

}
