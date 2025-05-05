package com.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.ZonedDateTime;

@Service
public class JwtTokenServiceImpl {
	private static final String SECRET_KEY = "moneyManager-key";
	private static final String ISSUER = "moneyManager-api";

	public String generateToken(UserDetailsImpl user) {
		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
		return com.auth0.jwt.JWT.create()
				.withIssuer(ISSUER)
				.withIssuedAt(creationDate())
				.withExpiresAt(expirationDate())
				.withSubject(user.getUsername())
				.sign(algorithm);
	}

	public String getSubject(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
			return JWT.require(algorithm)
					.withIssuer(ISSUER)
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			throw new RuntimeException("Invalid or expired JWT token");
		}
	}

	private Instant creationDate() {
		return ZonedDateTime.now().toInstant();
	}

	private Instant expirationDate() {
		return ZonedDateTime.now().plusHours(1).toInstant();
	}
}
