package com.ricardotravez.cuentamovimientos.service.impl;

import com.ricardotravez.cuentamovimientos.cuentaapi.CuentaApi;
import com.ricardotravez.cuentamovimientos.dto.ClienteDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaReporteDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaReporteDetalleDTO;
import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;
import com.ricardotravez.cuentamovimientos.entity.Cuenta;
import com.ricardotravez.cuentamovimientos.entity.MensajeError;
import com.ricardotravez.cuentamovimientos.exception.ClienteNoEncontradoException;
import com.ricardotravez.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.ricardotravez.cuentamovimientos.repository.CuentaRepository;
import com.ricardotravez.cuentamovimientos.service.CuentaService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaServiceImpl implements CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ModelMapper modelMapper;
    private final CuentaApi cuentaApi;

    private static final Logger logger = LoggerFactory.getLogger(CuentaServiceImpl.class);

    @Override
    public CuentaDTO crear(CuentaDTO cuentaDTO) {
        try {
            Observable<ClienteDTO> clienteObservable = Observable.fromCallable(() ->
                            cuentaApi.getCientePorId(cuentaDTO.getIdCliente()))
                    .subscribeOn(Schedulers.io())
                    .doOnError(error -> logger.error("Error obteniendo cliente: ", error))
                    .onErrorReturnItem(new ClienteDTO()); // TODO Devuelve null en caso de error

            // TODO Bloquea hasta que se obtiene el cliente y continúa con la lógica de creación
            ClienteDTO clienteDTO = clienteObservable.blockingFirst();

            if (clienteDTO.getId() == null) {
                throw new ClienteNoEncontradoException("");
            }

            Cuenta cuenta = modelMapper.map(cuentaDTO, Cuenta.class);
            return modelMapper.map(cuentaRepository.save(cuenta), CuentaDTO.class);
        } catch (Exception e) {
            throw new ClienteNoEncontradoException("Error: " + e.getMessage());
        }
    }


    @Override
    public List<CuentaDTO> listar() {
        return cuentaRepository.findAll().stream().map(
                (cuenta) -> modelMapper.map(cuenta, CuentaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CuentaDTO obtenerPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString()));
        return modelMapper.map(cuenta, CuentaDTO.class);
    }

    @Override
    public CuentaDTO actualizar(CuentaDTO cuentaDTO) {
        CuentaDTO cuentaDTODB = obtenerPorId(cuentaDTO.getId());

        cuentaDTODB.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuentaDTODB.setFecha(cuentaDTO.getFecha());
        cuentaDTODB.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuentaDTODB.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuentaDTODB.setEstado(cuentaDTO.isEstado());

        Cuenta cuenta = modelMapper.map(cuentaDTODB, Cuenta.class);

        return modelMapper.map(cuentaRepository.save(cuenta), CuentaDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        cuentaRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString()));
        cuentaRepository.deleteById(id);
    }


    @Override
    public List<CuentaDTO> findByIdCliente(String idCliente) {
        List<Cuenta> cuentas = cuentaRepository.findByIdCliente(idCliente);
        if (cuentas.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe ningún usuario con el ID proporcionado: " + idCliente);
        }
        return cuentas.stream().map(cuenta -> {
            CuentaDTO cuentaDTO = modelMapper.map(cuenta, CuentaDTO.class);
            List<MovimientoDTO> movimientoDTOs = cuenta.getMovimientos()
                    .stream()
                    .map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class))
                    .collect(Collectors.toList());
            cuentaDTO.setMovimientos(movimientoDTOs);
            return cuentaDTO;
        }).collect(Collectors.toList());
    }


    @Override
    public List<CuentaReporteDTO> obtenerReporteCuentaCliente(Long idCliente, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        try {

            // Establecer conexion con servicio cliente para obtener datos de cliente
            Observable<ClienteDTO> clienteObservable = Observable.fromCallable(() ->
                            cuentaApi.getCientePorId(idCliente.toString()))
                    .subscribeOn(Schedulers.io())
                    .doOnError(error -> logger.error("Error obteniendo cliente: ", error))
                    .onErrorReturnItem(new ClienteDTO());

            ClienteDTO clienteDTO = clienteObservable.blockingFirst();

            if (clienteDTO.getId() == null) {
                throw new ClienteNoEncontradoException("Error cliente no existe no se puede generar estado de cuentas");
            }

            // TODO Buscar la cuenta y sus movimientos utilizando el repositorio
            List<Cuenta> cuentas = cuentaRepository.findByIdClienteAndFechaBetween(idCliente.toString(), fechaInicio, fechaFin);

            // Verificar si la cuenta es nula
            if (cuentas.isEmpty()) {
                throw new RecursoNoEncontradoException("El cliente no contiene ninguna cuenta asociada");
            }

            List<CuentaReporteDTO> cuentaReporteDTOS = new ArrayList<>();

            cuentas.forEach(cuenta -> {
                // Mapear los movimientos de la cuenta a DTOs utilizando ModelMapper
                List<CuentaReporteDetalleDTO> cuentaReporteDetalleDTOS = cuenta.getMovimientos().stream()
                        .map(movimiento -> {
                            CuentaReporteDetalleDTO detalleDTO = modelMapper.map(movimiento, CuentaReporteDetalleDTO.class);
                            detalleDTO.setFecha(movimiento.getFecha().toLocalDate());
                            return detalleDTO;
                        })
                        .collect(Collectors.toList());

                // Mapear la cuenta a un DTO
                CuentaReporteDTO cuentaReporteDTO = modelMapper.map(cuenta, CuentaReporteDTO.class);

                cuentaReporteDTO.setCuentaReporteDetalle(cuentaReporteDetalleDTOS);
                cuentaReporteDTO.setFecha(LocalDate.now());
                cuentaReporteDTO.setCliente(clienteDTO);

                cuentaReporteDTOS.add(cuentaReporteDTO);
            });


            return cuentaReporteDTOS;
        } catch (RecursoNoEncontradoException ex) {
            // Capturar la excepción específica de RecursoNoEncontradoException y relanzarla
            throw ex;
        } catch (Exception e) {
            // Capturar cualquier otra excepción y registrarla antes de relanzarla
            log.error("Error al obtener el reporte de la cuenta: ", e);
            throw new RuntimeException("Error al obtener el reporte de la cuenta: " + e.getMessage(), e);
        }
    }

}
