package project.alexoshiro.registerapi.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import project.alexoshiro.registerapi.service.IPersonService;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class PersonControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	private String token = "";

	@Autowired
	private IPersonService personService;

	@BeforeAll
	public void init() throws Exception {
		MvcResult result = mockMvc.perform(post("/login").header("Authorization", "Basic YWRtaW46MTIz"))
				.andExpect(status().isOk())
				.andReturn();
		token = "Bearer " + result.getResponse().getHeader("x-token");
	}

	@Test
	public void getPeopleNoAuthorizationShouldReturnForbidden() throws Exception {
		mockMvc.perform(get("/people")).andExpect(status().isForbidden());
	}

	@Test
	public void getPersonByIdNoAuthorizationShouldReturnForbidden() throws Exception {
		mockMvc.perform(get("/people/1")).andExpect(status().isForbidden());
	}

	@Test
	public void registerPersonNoAuthorizationShouldReturnForbidden() throws Exception {
		mockMvc.perform(post("/people")).andExpect(status().isForbidden());
	}

	@Test
	public void updatePersonNoAuthorizationShouldReturnForbidden() throws Exception {
		mockMvc.perform(patch("/people/1")).andExpect(status().isForbidden());
	}

	@Test
	public void getPeopleShouldReturnList() throws Exception {
		mockMvc.perform(get("/people").header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.payload", hasSize(50)));
	}

	@Test
	public void getPersonByIdShouldReturnPerson() throws Exception {
		mockMvc.perform(get("/people/5e32fbb40d71210d2c4c2ab5").header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Teste")));
	}

	@Test
	public void registerPersonWithSuccessShouldReturnPerson() throws Exception {
		String json = "{\"name\":\"Tester\",\"gender\":\"MALE\",\"email\":\"tetes@example.com\","
				+ "\"birth_date\":\"1994-08-14\",\"naionality\":\"Brasil\",\"citizenship\":\"brasileiro\",\"cpf\":\"841.925.620-06\"}";
		mockMvc.perform(post("/people").header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Tester")));
	}

	@Test
	public void updatePersonWithSuccessShouldReturnPersonWithChangedInformation() throws Exception {
		String newEmail = "usuarioteste@example.com";
		String json = "{\"email\":\"" + newEmail + "\"}";
		mockMvc.perform(patch("/people/5e32fbb40d71210d2c4c2ab5").header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(newEmail)));
	}

	@Test
	public void deletePersonWithSuccessShouldReturnNoContent() throws Exception {
		mockMvc.perform(delete("/people/5e32fbb40d71210d2c4c2ab5").header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}

}
