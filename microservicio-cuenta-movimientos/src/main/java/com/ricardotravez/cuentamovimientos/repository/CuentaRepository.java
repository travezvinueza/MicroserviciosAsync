package com.ricardotravez.cuentamovimientos.repository;

import com.ricardotravez.cuentamovimientos.entity.Cuenta;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByIdCliente(String idCliente);

    // @EntityGraph en tu repositorio permite que JPA realice una única consulta para obtener las cuentas y sus movimientos asociados, evitando el problema de "N+1 selects
    @EntityGraph(attributePaths = "movimientos")
    @Query("SELECT c FROM Cuenta c WHERE c.idCliente = :idCliente AND c.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Cuenta> findByIdClienteAndFechaBetween(@Param("idCliente") String idCliente,
                                                @Param("fechaInicio") LocalDateTime fechaInicio,
                                                @Param("fechaFin") LocalDateTime fechaFin);
}
