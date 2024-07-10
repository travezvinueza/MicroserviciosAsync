package com.ricardotravez.clientepersona.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Cliente extends Persona {
    @Id
    @GeneratedValue
    private Long id;
    private String contrasena;
    private boolean estado;
}
