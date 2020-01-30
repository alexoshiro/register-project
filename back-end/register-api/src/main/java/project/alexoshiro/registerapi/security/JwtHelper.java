package project.alexoshiro.registerapi.security;

import java.util.Date;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtHelper {

	public String createToken(UserDetails user, Map<String, Object> claims) {
		long currentTime = System.currentTimeMillis();
		long oneHour = 1000l * 60l * 60l * 1l;
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(currentTime))
				.setExpiration(new Date(currentTime + oneHour))
				.addClaims(claims)
				.signWith(SecretKeyUtil.getSecretKey())
				.compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		try {
			Claims claims = extractClaims(token);
			return claims.getSubject().equals(userDetails.getUsername())
					&& new Date().before(claims.getExpiration());
		} catch (SignatureException e) {
			return false;
		}
	}

	public Claims extractClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SecretKeyUtil.getSecretKey())
				.parseClaimsJws(token)
				.getBody();
	}
}
