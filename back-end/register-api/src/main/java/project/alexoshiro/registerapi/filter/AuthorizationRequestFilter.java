package project.alexoshiro.registerapi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.security.SignatureException;
import project.alexoshiro.registerapi.dto.ErrorNormalizerDTO;
import project.alexoshiro.registerapi.security.JwtHelper;
import project.alexoshiro.registerapi.service.impl.LoginService;

@Component
public class AuthorizationRequestFilter extends OncePerRequestFilter {

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtHelper jwtHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		String jwt = null;
		String username = null;

		if (authorization != null && authorization.startsWith("Bearer ")) {
			jwt = authorization.substring(7);
			try {
				username = jwtHelper.extractClaims(jwt).getSubject();
			} catch (SignatureException e) {
				List<String> errors = new ArrayList<>();
				errors.add("Token de autenticação inválido.");
				ErrorNormalizerDTO body = new ErrorNormalizerDTO(String.valueOf(HttpStatus.UNAUTHORIZED.value()), errors);
				response.setHeader("Content-Type", "application/json;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write(new ObjectMapper().writeValueAsString(body));
				return;
			}
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = loginService.loadUserByUsername(username);

				if (jwtHelper.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(token);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
