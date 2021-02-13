package com.foxminded.warehouse.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.foxminded.warehouse.entities.AuthRequest;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class.getName());

	public boolean validateToken(String token) {
		logger.debug("Validating token :{} ", token);
		if (token != null && !token.contains(":")) {
			logger.debug("Token is valid");
			return true;
		}
		logger.warn("Token is invalid. Access denied");
		return false;
	}

	public String getLoginFromToken(String token) {
		logger.debug("Getting login from token:{}", token);
		byte[] credDecoded = Base64.getDecoder().decode(token);
		String credentials = new String(credDecoded, StandardCharsets.UTF_8);
		final String[] values = credentials.split(":", 2);
		logger.debug("Login from token is:{}", token);
		return values[1];
	}

	public String generateToken(AuthRequest request) {
		logger.debug("Genegating token for username:{} and password:{}", request.getUsername(), request.getPassword());
		String token = Base64.getEncoder()
				.encodeToString((request.getUsername() + ":" + request.getPassword()).getBytes());
		logger.debug("Generated token is:{}", token);
		return token;
	}

}