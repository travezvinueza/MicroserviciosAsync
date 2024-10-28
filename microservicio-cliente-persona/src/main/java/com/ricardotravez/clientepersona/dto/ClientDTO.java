package com.ricardotravez.clientepersona.dto;

import com.ricardotravez.clientepersona.dto.enums.GenderPerson;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private GenderPerson genderPerson;
    private int age;
    @Pattern(regexp = "\\d{10}", message = "El número de identificación debe contener solo dígitos y tener un máximo de 10 caracteres")
    private String identificationNumber;
    private String address;
    private LocalDate date;
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe contener solo dígitos y tener un máximo de 10 caracteres")
    private String phone;
    @Min(value = 6, message = "El valor de la clave debe ser mayor o igual a 6")
    private String password;
    private boolean state;
    private List<AccountDTO> accounts;
}
