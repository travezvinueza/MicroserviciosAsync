package com.ricardotravez.cuentamovimientos.service.impl;

import com.ricardotravez.cuentamovimientos.dto.MotionDTO;
import com.ricardotravez.cuentamovimientos.entity.Account;
import com.ricardotravez.cuentamovimientos.dto.MessageError;
import com.ricardotravez.cuentamovimientos.entity.Motion;
import com.ricardotravez.cuentamovimientos.enums.TransactionType;
import com.ricardotravez.cuentamovimientos.exception.AccountNotFoundException;
import com.ricardotravez.cuentamovimientos.exception.ResourceNotFoundException;
import com.ricardotravez.cuentamovimientos.exception.SaldoInsuficienteException;
import com.ricardotravez.cuentamovimientos.repository.AccountRepository;
import com.ricardotravez.cuentamovimientos.repository.MotionRepository;
import com.ricardotravez.cuentamovimientos.service.MotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MotionServiceImpl implements MotionService {
    private final MotionRepository motionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public MotionDTO create(MotionDTO motionDTO) {
        // Busca la cuenta por el número de cuenta en el repositorio. Si no se encuentra, lanza una excepción.
        Account account = accountRepository.findByAccountNumber(motionDTO.getAccountNumber()).orElseThrow(
                () -> new AccountNotFoundException(MessageError.CUENTA_NO_ENCONTRADA.toString()));

        motionDTO.setDate(LocalDateTime.now());
        Motion motion = modelMapper.map(motionDTO, Motion.class);
        motion.setAccount(account);  // Establece la cuenta asociada al movimiento.

        // Obtiene el último movimiento realizado en la cuenta.
        Optional<Motion> optionalMovimiento = motionRepository.obtenerUltimoMovimientoPorAccountNumber(account.getAccountNumber());

        double saldoActual = account.getInitialBalance();
        if (optionalMovimiento.isPresent()) {
            saldoActual = optionalMovimiento.get().getSaldo();
        }

        log.info("Cuenta: {}", account);

        if (motion.getTransactionType() == TransactionType.DEPOSITO) {
            motion.setSaldo(saldoActual + motion.getValor());
        } else if (motion.getTransactionType() == TransactionType.RETIRO) {
            if (saldoActual < motion.getValor()) {
                throw new SaldoInsuficienteException(MessageError.SALDO_NO_DISPONIBLE.toString());
            } else {
                motion.setSaldo(saldoActual - motion.getValor());
            }
        }
        // Guarda el movimiento en el repositorio y mapea la entidad de movimiento a DTO.
        Motion savedMotion = motionRepository.save(motion);
        return modelMapper.map(savedMotion, MotionDTO.class);
    }

    @Override
    public List<MotionDTO> list() {
        return motionRepository.findAll().stream()
                .map(motion -> modelMapper.map(motion, MotionDTO.class))
                .toList();
    }

    @Override
    public MotionDTO getById(Long id) {
        Motion motion = motionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(MessageError.RECURSO_NO_ENCONTRADO.toString())
        );
        return modelMapper.map(motion, MotionDTO.class);
    }

    @Override
    public MotionDTO update(MotionDTO motionDTO) {
        Motion motion = modelMapper.map(getById(motionDTO.getId()), Motion.class);

        motion.setDate(motionDTO.getDate());
        motion.setTransactionType(motionDTO.getTransactionType());
        motion.setValor(motionDTO.getValor());
        motion.setSaldo(motionDTO.getSaldo());

        return modelMapper.map(motionRepository.save(motion), MotionDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        motionRepository.delete(
                motionRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(MessageError.RECURSO_NO_ENCONTRADO.toString())
                )
        );
    }

    @Override
    public List<MotionDTO> findByIdClient(String idClient) {
        List<Motion> motions = motionRepository.findByIdClient(idClient);
        if (motions.isEmpty()) {
            throw new ResourceNotFoundException("No existe ningún usuario con el ID proporcionado: " + idClient);
        }
        return motions.stream().map(motion -> modelMapper.map(motion, MotionDTO.class)).toList();
    }

}
