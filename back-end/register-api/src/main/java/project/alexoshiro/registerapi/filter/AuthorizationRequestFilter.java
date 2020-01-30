package project.alexoshiro.registerapi.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
			username = jwtHelper.extractClaims(jwt).getSubject();

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
