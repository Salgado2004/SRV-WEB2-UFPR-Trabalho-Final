package br.ufpr.webII.trabalhoFinal.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String BASE_URL = "/service/v1";
    private static final String[] ALLOWED_ORIGINS = {
        "http://localhost:4200"
    };
    
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Auth Controller
        addCorsMapping(registry, BASE_URL + "/auth/register", "POST");
        addCorsMapping(registry, BASE_URL + "/auth/login", "POST");
        
        // Employee Controller
        addCorsMapping(registry, BASE_URL + "/employee", "POST", "GET");
        addCorsMapping(registry, BASE_URL + "/employee/{userId}", "DELETE", "PUT");
        
        // Equipment Category Controller        
        addCorsMapping(registry, BASE_URL + "/equipment-category", "POST", "GET", "PUT");
        addCorsMapping(registry, BASE_URL + "/equipment-category/{id}", "DELETE");

        // Receipt Controller
        addCorsMapping(registry, BASE_URL + "/receipt", "GET");

        // Requests Controller        
        addCorsMapping(registry, BASE_URL + "/requests", "POST", "GET");
        addCorsMapping(registry, BASE_URL + "/requests/{id}", "GET", "PUT");

        // Reports Controller
        addCorsMapping(registry, BASE_URL + "/requests/report", "GET");
        addCorsMapping(registry, BASE_URL + "/requests/report/category", "GET");
    }

    private void addCorsMapping(CorsRegistry registry, String path, String... methods) {
        registry.addMapping(path)
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(methods);
    }
}
