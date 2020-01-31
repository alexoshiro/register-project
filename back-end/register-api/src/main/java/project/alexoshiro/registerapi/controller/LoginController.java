package project.alexoshiro.registerapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.alexoshiro.registerapi.dto.ErrorNormalizerDTO;
import project.alexoshiro.registerapi.security.JwtHelper;
import project.alexoshiro.registerapi.service.impl.LoginService;

@RestController
@RequestMapping
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtHelper jwtHelper;

	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestHeader("Authorization") String basic) {
		String decodedBasic = decodeBasicRequest(basic);
		List<String> errors = new ArrayList<>();
		if (decodedBasic != null) {
			String username = decodedBasic.split(":")[0];
			String password = decodedBasic.split(":")[1];
			try {
				authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			} catch (BadCredentialsException e) {
				errors.add("Username or Passowrd invalid");
				ErrorNormalizerDTO dto = new ErrorNormalizerDTO(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
						errors);
				return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
			}
			UserDetails user = loginService.loadUserByUsername(username);
			String jwt = jwtHelper.createToken(user, new HashMap<>());
			return ResponseEntity.ok().header("x-token", jwt).build();
		}
		errors.add("Invalid credentials information");
		ErrorNormalizerDTO dto = new ErrorNormalizerDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()),
				errors);
		return ResponseEntity.badRequest().body(dto);
	}

	private String decodeBasicRequest(String basic) {
		if (basic != null && !basic.isBlank() && basic.startsWith("Basic ") && Base64.isBase64(basic.substring(6))) {
			String decodedBasic = new String(Base64.decodeBase64(basic.substring(6)));
			if (decodedBasic.contains(":") && decodedBasic.split(":")[0].length() > 0
					&& decodedBasic.split(":")[1].length() > 0) {
				return decodedBasic;
			}
		}
		return null;
	}
}
