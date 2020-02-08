package com.spring.tdd.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private UserPrincipalService userPrincipalService;
	
	public SecurityConfiguration(UserPrincipalService userPrincipalService) {
		super();
		this.userPrincipalService = userPrincipalService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.inMemoryAuthentication() .withUser("admin")
		 * .password(encoder().encode("Admin@123")) .roles("ADMIN") .and()
		 * .withUser("user") .password(encoder().encode("User@123")) .roles("USER");
		 */
		
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("/").authenticated().and()
		.csrf().disable()
		.authorizeRequests().antMatchers("/api/users/**").hasRole("ADMIN").and()
		.csrf().disable()
		.authorizeRequests().antMatchers("/api/todo/**").hasAnyRole("USER","ADMIN").and()
		.formLogin().successHandler(new LoginSuccessHandler()).and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(encoder());
		provider.setUserDetailsService(userPrincipalService);
		return provider;
	}
}
