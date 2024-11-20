package br.ufpr.webII.trabalhoFinal.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String BASE_URL = "/service/v1";
    private static final String[] ALLOWED_ORIGINS = {
        "http://localhost:3000"
    };
    
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Auth Controller
        addCorsMapping(registry, BASE_URL + "/auth/register", new String[]{"POST"});
        addCorsMapping(registry, BASE_URL + "/auth/login", new String[]{"POST"});
        
        // Employee Controller
        addCorsMapping(registry, BASE_URL + "/employee", new String[]{"POST", "GET"});
        addCorsMapping(registry, BASE_URL + "/employee/{userId}", new String[]{"DELETE", "PUT"});
        
        // Equipment Category Controller        
        addCorsMapping(registry, BASE_URL + "/equipment-category", new String[]{"POST", "GET", "PUT"});
        addCorsMapping(registry, BASE_URL + "/equipment-category/{id}", new String[]{"DELETE"});

        // Receipt Controller
        addCorsMapping(registry, BASE_URL + "/receipt", new String[]{"GET"});

        // Requests Controller        
        addCorsMapping(registry, BASE_URL + "/requests", new String[]{"POST", "GET"});
        addCorsMapping(registry, BASE_URL + "/requests/{id}", new String[]{"GET", "PUT"});
    }

    private void addCorsMapping(CorsRegistry registry, String path, String[] methods) {
        registry.addMapping(path)
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(methods);
    }
}
