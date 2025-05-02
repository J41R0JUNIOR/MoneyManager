package com.demo.security;

import com.demo.model.User;
import com.demo.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenServiceImpl jwtTokenService;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (isPublicEndpoint(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = recoveryToken(request);

		if (token != null) {
			String subject = jwtTokenService.getSubject(token);
			User user = userRepository.findUserByEmail(subject).orElseThrow();

			UserDetailsImpl userDetails = new UserDetailsImpl(user);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing or invalid");
			return;
		}

		filterChain.doFilter(request, response);
	}


	private String recoveryToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader == null) {
			return null;
		}

		return authorizationHeader.replace("Bearer ", "");
	}
	private boolean isPublicEndpoint(HttpServletRequest request) {
		String[] publicEndpoints = {"/auth/signIn", "/auth/signUp", "/test/user", "/test/adm", "user/getAll"};

		for (String endpoint : publicEndpoints) {
			if (request.getRequestURI().contains(endpoint)) {
				return true;
			}
		}
		return false;
	}

}
