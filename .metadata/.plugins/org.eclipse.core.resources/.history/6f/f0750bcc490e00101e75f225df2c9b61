package com.mx.APIGatwey.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc

public class CorsConfig{
	
	  public void addCorsMappings(CorsRegistry registry) {
	
		registry.addMapping("/**").allowedHeaders("*").allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST", "PUT", "DELETE").allowCredentials(true);
	}

}
