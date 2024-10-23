package com.ricardotravez.cuentamovimientos.entity;

import com.ricardotravez.cuentamovimientos.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "motions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Motion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    private double valor;
    private double saldo;
    @Column(name = "id_client")
    private String idClient;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
