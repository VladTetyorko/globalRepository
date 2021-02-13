package com.foxminded.warehouse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.foxminded.warehouse.services.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtFilter jwtFilter;
	private final AuthenticationEntryPointImpl authenticationEntryPoint;
	private final UserDetailsService userDetailsService;

	public SecurityConfig(JwtFilter jwtFilter, AuthenticationEntryPointImpl authenticationEntryPoint,
			UserService userService) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.jwtFilter = jwtFilter;
		this.userDetailsService = userService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.DELETE).hasRole("ADMIN").antMatchers(HttpMethod.PUT).hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/v1/warehouse/**").hasRole("ADMIN")
				.antMatchers("/api/v1/warehouse/admin/**").hasRole("ADMIN").antMatchers("/api/v1/warehouse/**")
				.hasAnyRole("USER", "ADMIN").antMatchers("/api/v1/warehouse/login").permitAll().and()
				.authorizeRequests().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint).and().csrf().disable();
		http.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/api/v1/warehouse/login");
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}