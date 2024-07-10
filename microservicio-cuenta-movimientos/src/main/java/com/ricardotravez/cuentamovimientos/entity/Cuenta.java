package com.ricardotravez.cuentamovimientos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cuentas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numeroCuenta;
    private String tipoCuenta;
    private LocalDateTime fecha = LocalDateTime.now();
    private double saldoInicial;
    private boolean estado;
    private String idCliente;
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Movimiento> movimientos;
}
