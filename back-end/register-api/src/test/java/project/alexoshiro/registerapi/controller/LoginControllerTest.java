package project.alexoshiro.registerapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import project.alexoshiro.registerapi.security.JwtHelper;
import project.alexoshiro.registerapi.service.impl.LoginService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthenticationManager authManager;

	@MockBean
	private LoginService loginService;

	@MockBean
	private JwtHelper jwtHelper;

	@Test
	public void doLoginShouldReturnTokenInHeader() throws Exception {
		UserDetails user = new User("teste", "teste", new ArrayList<>());
		given(loginService.loadUserByUsername(any())).willReturn(user);

		given(jwtHelper.createToken(any(), any())).willReturn(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.header("Authorization", "Basic dGVzdGU6dGVzdGU="))
				.andExpect(status().isOk()).andReturn();

		String headerValue = mvcResult.getResponse().getHeader("x-token");
		assertNotNull(headerValue);
	}

	@Test
	public void doLoginWithWrongCredentialsShouldReturnUnauthorized() throws Exception {
		given(authManager.authenticate(any())).willThrow(BadCredentialsException.class);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.header("Authorization", "Basic dGVzdGU6dGVzdGU="))
				.andExpect(status().isUnauthorized()).andReturn();

		String body = mvcResult.getResponse().getContentAsString();
		assertEquals("Username or Passowrd invalid", body);
	}

	@Test
	public void doLoginWithInvalidCredentialsShouldReturnBadRequest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.header("Authorization", "dGVzdGU6dGVzdGU="))
				.andExpect(status().isBadRequest()).andReturn();

		String body = mvcResult.getResponse().getContentAsString();
		assertEquals("Invalid credentials information", body);
	}
}
