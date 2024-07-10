package com.ricardotravez.cuentamovimientos.service.impl;

import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;
import com.ricardotravez.cuentamovimientos.entity.Cuenta;
import com.ricardotravez.cuentamovimientos.entity.MensajeError;
import com.ricardotravez.cuentamovimientos.entity.Movimiento;
import com.ricardotravez.cuentamovimientos.entity.TipoMovimiento;
import com.ricardotravez.cuentamovimientos.exception.CuentaNoEncontradaException;
import com.ricardotravez.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.ricardotravez.cuentamovimientos.exception.SaldoInsuficienteException;
import com.ricardotravez.cuentamovimientos.repository.CuentaRepository;
import com.ricardotravez.cuentamovimientos.repository.MovimientoRepository;
import com.ricardotravez.cuentamovimientos.service.MovimientoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final ModelMapper modelMapper;

    @Override
    public MovimientoDTO crear(MovimientoDTO movimientoDTO) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoDTO.getNumeroCuenta()).orElseThrow(
                () -> new CuentaNoEncontradaException(MensajeError.CUENTA_NO_ENCONTRADA.toString()));

        movimientoDTO.setFecha(LocalDateTime.now());
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        movimiento.setCuenta(cuenta);
        Optional<Movimiento> optionalMovimiento = movimientoRepository.obtenerUltimoMovimientoPorNumeroCuenta(cuenta.getNumeroCuenta());

        log.info("Cuenta: {}", cuenta);

        //hay movimientos
        if (optionalMovimiento.isPresent()) {
            log.info("Tipo Movimiento: {}", TipoMovimiento.DEPOSITO);
            if (movimiento.getTipoMovimiento().toUpperCase().equals(TipoMovimiento.DEPOSITO.toString())) {
                movimiento.setSaldo(optionalMovimiento.get().getSaldo() + movimiento.getValor());
            }
            if (movimiento.getTipoMovimiento().toUpperCase().equals(TipoMovimiento.RETIRO.toString())) {
                if (optionalMovimiento.get().getSaldo() < movimiento.getValor()) {
                    throw new SaldoInsuficienteException(MensajeError.SALDO_NO_DISPONIBLE.toString());
                } else {
                    if (movimiento.getValor() < 0) {
                        movimiento.setSaldo(optionalMovimiento.get().getSaldo() - (movimiento.getValor() * -1));
                    } else {
                        movimiento.setSaldo(optionalMovimiento.get().getSaldo() - movimiento.getValor());
                    }
                }
            }

        } else {// no hay movimientos
            if (movimiento.getTipoMovimiento().toUpperCase().equals(TipoMovimiento.DEPOSITO.toString())) {
                movimiento.setSaldo(cuenta.getSaldoInicial() + movimiento.getValor());
            } else {
                if (movimiento.getTipoMovimiento().toUpperCase().equals(TipoMovimiento.RETIRO.toString())) {
                    if (cuenta.getSaldoInicial() < movimiento.getValor()) {
                        throw new SaldoInsuficienteException(MensajeError.SALDO_NO_DISPONIBLE.toString());
                    } else {
                        if (movimiento.getValor() < 0) {
                            movimiento.setSaldo(cuenta.getSaldoInicial() - (movimiento.getValor() * -1));
                        } else {
                            movimiento.setSaldo(cuenta.getSaldoInicial() - movimiento.getValor());
                        }
                    }

                }
            }

        }
        return modelMapper.map(movimientoRepository.save(movimiento), MovimientoDTO.class);
    }

    @Override
    public List<MovimientoDTO> listar() {
        return movimientoRepository.findAll().stream().map(
                (movimiento )-> modelMapper.map(movimiento,MovimientoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MovimientoDTO obtenerPorId(Long id) {
        Movimiento movimiento= movimientoRepository.findById(id).orElseThrow(
                ()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString())
        );
        return modelMapper.map(movimiento,MovimientoDTO.class);
    }

    @Override
    public MovimientoDTO actualizar(MovimientoDTO movimientoDTO) {
        Movimiento movimientoDB = modelMapper.map(obtenerPorId(movimientoDTO.getId()), Movimiento.class);

        movimientoDB.setFecha(movimientoDTO.getFecha());
        movimientoDB.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimientoDB.setValor(movimientoDTO.getValor());
        movimientoDB.setSaldo(movimientoDTO.getSaldo());

        return modelMapper.map(movimientoRepository.save(movimientoDB),MovimientoDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        movimientoRepository.findById(id).orElseThrow(
                ()-> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString())
        );
        movimientoRepository.deleteById(id);
    }
    @Override
    public List<MovimientoDTO> findByIdCliente(String idCliente) {
        List<Movimiento> movimientos = movimientoRepository.findByIdCliente(idCliente);
        if (movimientos.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe ningún usuario con el ID proporcionado: " + idCliente);
        }
        return movimientos.stream().map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class)).collect(Collectors.toList());
    }
}
