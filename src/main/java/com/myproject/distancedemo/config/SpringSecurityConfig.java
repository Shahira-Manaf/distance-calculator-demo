package com.myproject.distancedemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER").and().withUser("admin")
				.password("{noop}password").roles("USER", "ADMIN");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/findDistance/**").hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/findAll/**").hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/findById/**").hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/findByPostcode/**").hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.POST, "/createPostcode").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/updateById/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/updateByPostcode/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/deleteById/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/deleteByPostcode/**").hasRole("ADMIN").and().csrf().disable().formLogin()
				.disable();
	}
}