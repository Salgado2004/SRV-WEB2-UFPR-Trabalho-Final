/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.auth;

import br.ufpr.webII.trabalhoFinal.infra.exceptions.TokenException;
import br.ufpr.webII.trabalhoFinal.infra.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author mateus
 */

@Component
public class UserAuthentication extends OncePerRequestFilter {

    TokenService tokenSrv = new TokenService();
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String autorizationURL = request.getRequestURI();
      //  int contem = autorizationURL.contains("employee") ? 1 : autorizationURL.contains("customer") ? 2 : autorizationURL.contains("request") ? 3 : autorizationURL.contains("category") ? 4 : autorizationURL.contains("receipt") ? 5 : 0;
        
        String[] partes = new String[7];
        partes = autorizationURL.split("/");
        
        switch (partes[2]){
            case ("employee"):
                if (tokenSrv.getProfile(authorizationHeader).equalsIgnoreCase("Employee")){
                    /*follow your life :D;*/
                    filterChain.doFilter(request, response);
                } else  
                    throw new TokenException("Token não autorizado");
                break;
            case ("customer"):
                break;
            case ("request"):
                break;
            case ("category"):
                break;
            case ("receipt"):
                break;
            default:
                break;
        }
    }
    
    
}
