package com.ricardotravez.clientepersona;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardotravez.clientepersona.dto.ClienteDTO;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MicroservicioClientePersonaApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Faker faker = new Faker();

	@Test
	@DisplayName("Test 1: crear cliente")
	public void crearCliente() throws Exception {
		ClienteDTO clienteDTO = new ClienteDTO(
				null,
				faker.name().firstName(),
				"Masculino",
				29,
				faker.idNumber().validEnZaSsn(),
				faker.address().streetName(),
				LocalDate.now(),
				faker.phoneNumber().cellPhone(),
				"123456",
				true,
				new ArrayList<>()
		);
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/clientes/crear")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(clienteDTO)))
				.andExpect(status().isCreated());
	}

}
