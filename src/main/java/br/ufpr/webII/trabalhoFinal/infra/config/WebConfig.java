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
        addCorsMapping(registry, BASE_URL + "/auth/register", "POST");
        addCorsMapping(registry, BASE_URL + "/auth/login", "POST");
        
        // Employee Controller
        addCorsMapping(registry, BASE_URL + "/employee/new", "POST");
        addCorsMapping(registry, BASE_URL + "/employee/detail/:id", "GET");
        addCorsMapping(registry, BASE_URL + "/employee/delete/:id", "DELETE");
        addCorsMapping(registry, BASE_URL + "/employee/update/:id", "PUT");
        
        // Equipment Category Controller        
        addCorsMapping(registry, BASE_URL + "/equipment-category/new", "POST");
        addCorsMapping(registry, BASE_URL + "/equipment-category/list", "GET");
        addCorsMapping(registry, BASE_URL + "/equipment-category/delete/{id}", "DELETE");
        addCorsMapping(registry, BASE_URL + "/equipment-category/update", "PUT");

        // Receipt Controller
        addCorsMapping(registry, BASE_URL + "/receipt/receitas", "GET");

        // Requests Controller        
        addCorsMapping(registry, BASE_URL + "/requests/new", "POST");
        addCorsMapping(registry, BASE_URL + "/requests/list", "GET");
        addCorsMapping(registry, BASE_URL + "/requests/detail/{id}", "GET");
        addCorsMapping(registry, BASE_URL + "/requests/update/{id}", "PUT");
    }

    private void addCorsMapping(CorsRegistry registry, String path, String method) {
        registry.addMapping(path)
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(method);
    }
}
