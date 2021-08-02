package com.itranslation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.itranslation.services.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	private final PasswordEncoder bCryptPasswordEncoder;

	public SecurityConfig(UserService userDetailsService, PasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/login**", "/css/style.css", "/user/new", "/user/save")
				.permitAll().antMatchers("/**").authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/").failureUrl("/login?error=true").permitAll().and().logout()
				.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll().and().csrf().disable();
		httpSecurity.sessionManagement().maximumSessions(3).maxSessionsPreventsLogin(false).expiredUrl("/login")
				.sessionRegistry(sessionRegistry());
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

    @Bean
    SessionRegistry sessionRegistry() {			
        return new SessionRegistryImpl();
    }
 
}
