package project.alexoshiro.registerapi.security.config;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import project.alexoshiro.registerapi.security.SecretKeyUtil;

@Component
public class CreateSecret {

	@PostConstruct
	private void initSecret() {
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		SecretKeyUtil.setSecretKey(key);
	}
}
