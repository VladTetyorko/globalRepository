package com.foxminded.warehouse.entities;

public class AuthResponse {
	private String token;

	public AuthResponse(String token2) {
		this.token = token2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}