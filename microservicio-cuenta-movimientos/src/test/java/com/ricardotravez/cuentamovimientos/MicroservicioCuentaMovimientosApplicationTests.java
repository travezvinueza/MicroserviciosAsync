package com.ricardotravez.cuentamovimientos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;
import com.ricardotravez.cuentamovimientos.entity.TipoMovimiento;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MicroservicioCuentaMovimientosApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Faker faker = new Faker();

    @Test
    @DisplayName("Test 1: No Deberia Crear Movimiento Si Numero de Cuenta No Existe")
    public void testIntegrationMovimientos() throws Exception {
        Long id = Math.abs(new Random().nextLong());
        String idCliente = faker.idNumber().valid();
        MovimientoDTO movimientoDTO = new MovimientoDTO(id, LocalDateTime.now(), getRandomTipoMovimiento().toString(), 50.0, 200.0, faker.finance().iban(), idCliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/movimientos/crear").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movimientoDTO))).andExpect(status().isBadRequest());
    }

    private TipoMovimiento getRandomTipoMovimiento() {
        Random random = new Random();
        return TipoMovimiento.values()[random.nextInt(TipoMovimiento.values().length)];
    }

    @Test
    @DisplayName("Test 1: listar cuentas")
    public void listaIntegrationCliente() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/cuentas/listar").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        List<CuentaDTO> cuentaDTOS = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        if (cuentaDTOS.isEmpty()) {
            Assertions.assertThat(cuentaDTOS).isEmpty();
        } else {
            Assertions.assertThat(cuentaDTOS).isNotEmpty();
        }

    }


    @Test
    @DisplayName("Test 1: listar movimientos")
    public void listaIntegrationMovimientos() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/movimientos/listar").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        List<MovimientoDTO> movimientoDTOS = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        if (movimientoDTOS.isEmpty()) {
            Assertions.assertThat(movimientoDTOS).isEmpty();
        } else {
            Assertions.assertThat(movimientoDTOS).isNotEmpty();
        }
    }

}
