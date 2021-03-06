package com.foxminded.warehouse.security;

import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.foxminded.warehouse.services.UserService;

@Component
public class JwtFilter extends GenericFilterBean {

	public static final String AUTHORIZATION = "Authorization";

	private final JwtProvider jwtProvider;

	private final UserService customUserDetailsService;

	public JwtFilter(UserService customUserDetailsService, JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		logger.info("do filter...");
		String token = getTokenFromRequest((HttpServletRequest) servletRequest);
		if (token != null && jwtProvider.validateToken(token)) {
			String userLogin = jwtProvider.getLoginFromToken(token);
			UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null,
					customUserDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader(AUTHORIZATION);
		if (hasText(bearer) && bearer.startsWith("Basic ")) {
			return bearer.substring(6);
		}
		return null;
	}
}