package com.mx.APIGatwey.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.GET).authenticated()
						.requestMatchers(HttpMethod.POST).permitAll()
						.requestMatchers(HttpMethod.DELETE).permitAll()
						.requestMatchers(HttpMethod.PUT).permitAll()
					.anyRequest().authenticated())
			.formLogin().and()
			.httpBasic()
			.and()
			.cors();
		return http.build();
	}
}
