package com.ricardotravez.clientepersona;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardotravez.clientepersona.dto.ClientDTO;
import com.ricardotravez.clientepersona.dto.enums.GenderPerson;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MicroservicioClientPersonApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Faker faker = new Faker();

	@Test
	@DisplayName("Test 1: create client")
	void createClient() throws Exception {
		ClientDTO clientDTO = new ClientDTO(
				null,
				faker.name().firstName(),
				GenderPerson.FEMENINO,
				29,
				faker.idNumber().validEnZaSsn(),
				faker.address().streetName(),
				LocalDate.now(),
				faker.phoneNumber().cellPhone(),
				"123456",
				true,
				new ArrayList<>()
		);
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/clients/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(clientDTO)))
				.andExpect(status().isCreated());
	}

}




