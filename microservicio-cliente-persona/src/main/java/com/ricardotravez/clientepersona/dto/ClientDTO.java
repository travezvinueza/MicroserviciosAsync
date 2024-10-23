package com.ricardotravez.clientepersona.dto;

import com.ricardotravez.clientepersona.dto.enums.GenderPerson;
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
    private String identificationNumber;
    private String address;
    private LocalDate date;
    private String phone;
    private String password;
    private boolean state;
    private List<AccountDTO> accounts;
}
