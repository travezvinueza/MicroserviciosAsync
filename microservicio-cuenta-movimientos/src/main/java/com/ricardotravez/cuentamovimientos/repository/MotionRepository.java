package com.ricardotravez.cuentamovimientos.repository;

import com.ricardotravez.cuentamovimientos.entity.Motion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MotionRepository extends JpaRepository<Motion, Long> {
    List<Motion> findByIdClient(String idClient);
    @Query("SELECT m FROM Motion m JOIN m.account c WHERE c.accountNumber = ?1 ORDER BY m.id DESC LIMIT 1")
    Optional<Motion> obtenerUltimoMovimientoPorAccountNumber(String accountNumber);
}