package project.alexoshiro.registerapi.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import project.alexoshiro.registerapi.security.JwtHelper;
import project.alexoshiro.registerapi.service.impl.LoginService;


public class LoginControllerTest extends BaseController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtHelper jwtHelper;

	@Test
	public void doLogin() throws Exception {
		MvcResult result = mockMvc.perform(post("/login").header("Authorization", "Basic YWRtaW46MTIz"))
				.andExpect(status().isOk())
				.andReturn();
		String token = result.getResponse().getHeader("x-token");
		assertNotNull(token);
	}

	@Test
	public void doLoginWhenCredentialsAreWrongShouldReturnUnauthorized() throws Exception {
		mockMvc.perform(post("/login").header("Authorization", "Basic YWRtaW46MTI"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void doLoginWhenNoCredentialsShouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/login"))
				.andExpect(status().isBadRequest());
	}
}
