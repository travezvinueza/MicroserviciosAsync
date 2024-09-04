package com.ricardotravez.clientepersona.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Client extends Person {
    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private boolean state;
}
