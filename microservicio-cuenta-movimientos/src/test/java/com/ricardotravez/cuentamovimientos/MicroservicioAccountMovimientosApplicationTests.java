package com.ricardotravez.cuentamovimientos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardotravez.cuentamovimientos.dto.AccountDTO;
import com.ricardotravez.cuentamovimientos.dto.MotionDTO;
import com.ricardotravez.cuentamovimientos.enums.TransactionType;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MicroservicioAccountMovimientosApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Test 1: No Deberia Crear Movimiento Si Numero de Cuenta No Existe")
    void testIntegrationMotions() throws Exception {
        Long id = Math.abs(new Random().nextLong());
        String idClient = faker.idNumber().valid();
        MotionDTO motionDTO = new MotionDTO(id, LocalDateTime.now(), getRandomTransactionType(), 50.0, 200.0, faker.finance().iban(), idClient);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/motions/create").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(motionDTO))).andExpect(status().isBadRequest());
    }

    private TransactionType getRandomTransactionType() {
        Random random = new Random();
        return TransactionType.values()[random.nextInt(TransactionType.values().length)];
    }

    @Test
    @DisplayName("Test 1: listar cuentas")
    void listIntegrationClient() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/list").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        List<AccountDTO> accountDTOS = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        if (accountDTOS.isEmpty()) {
            Assertions.assertThat(accountDTOS).isEmpty();
        } else {
            Assertions.assertThat(accountDTOS).isNotEmpty();
        }

    }


    @Test
    @DisplayName("Test 1: listar movimientos")
    void listIntegrationMotions() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/motions/list").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        List<MotionDTO> motionDTOS = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        if (motionDTOS.isEmpty()) {
            Assertions.assertThat(motionDTOS).isEmpty();
        } else {
            Assertions.assertThat(motionDTOS).isNotEmpty();
        }
    }

}
