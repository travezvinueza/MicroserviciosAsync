package com.ricardotravez.cuentamovimientos.dto;

import com.ricardotravez.cuentamovimientos.enums.GenderPerson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private GenderPerson genderPerson;
    private int age;
    private String identificationNumber;
    private String address;
    private String phone;
}
