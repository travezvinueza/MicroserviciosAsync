package com.ricardotravez.cuentamovimientos.repository;

import com.ricardotravez.cuentamovimientos.entity.Cuenta;
import com.ricardotravez.cuentamovimientos.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByIdCliente(String idCliente);
    @Query("SELECT m FROM Movimiento m JOIN m.cuenta c WHERE c.numeroCuenta = ?1 ORDER BY m.id DESC LIMIT 1")
    Optional<Movimiento> obtenerUltimoMovimientoPorNumeroCuenta(String numeroCuenta);

    @Query("SELECT m FROM Movimiento m JOIN m.cuenta c WHERE c.numeroCuenta = :numeroCuenta ORDER BY m.id DESC")
    List<Movimiento> findAllByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

}
