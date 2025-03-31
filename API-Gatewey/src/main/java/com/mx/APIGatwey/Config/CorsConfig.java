package com.mx.APIGatwey.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration


public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir CORS desde el origen http://localhost:4200
        registry.addMapping("/**")  // Aplica a todas las rutas
                .allowedOrigins("http://localhost:4200")  // Origen permitido (tu frontend de Angular)
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos HTTP permitidos
                .allowedHeaders("*")  // Permite todos los encabezados
                .allowCredentials(true);  // Permite el uso de cookies y credenciales (si es necesario)
    }

}
