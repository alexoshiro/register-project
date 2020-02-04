package project.alexoshiro.registerapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseController {
	@Autowired
	protected MockMvc mockMvc;

	protected String token = "";

	@BeforeAll
	public void init() throws Exception {
		MvcResult result = mockMvc.perform(post("/login").header("Authorization", "Basic YWRtaW46MTIz"))
				.andExpect(status().isOk())
				.andReturn();
		token = "Bearer " + result.getResponse().getHeader("x-token");
	}
}
