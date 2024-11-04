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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author mateus
 */

@Component
public class UserAuthentication extends OncePerRequestFilter {

    @Autowired
    TokenService tokenSrv;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO: Implementar validação de token == null
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null)
            throw new TokenException("Token nulo. Refazer o login");
        authorizationHeader = authorizationHeader.replace("Bearer ", "");
        String autorizationURL = request.getRequestURI();
       
        String[] partes = new String[7];
        partes = autorizationURL.split("/");
        
        String usr = tokenSrv.getProfile(authorizationHeader);
        
        switch (partes[3]){ //o switch case foi alterado para o rule switch por recomendação da IDE. sintaxe está correta.
            case ("employee") -> {
                if (usr.equalsIgnoreCase("Employee")){
                    /*follow your life :D*/
                    filterChain.doFilter(request, response);
                } else  
                    throw new TokenException("Token não autorizado!!");
            }
            case ("customer") -> {
                if (usr.equalsIgnoreCase("Customer")){
                    filterChain.doFilter(request, response);
                } else
                    throw new TokenException("Token não autorizado!!");
            }
            case ("requests") -> {
                if (usr.equalsIgnoreCase("Employee")){
                    if (partes[4].equalsIgnoreCase("new"))
                        throw new TokenException("Um empregado não pode criar um request, se registre como cliente e tente novamente.");
                } else
                    filterChain.doFilter(request, response);
            }
            case ("equipment-category") -> {
                if (usr.equalsIgnoreCase("Employee")){
                    filterChain.doFilter(request, response);
                } else if(usr.equalsIgnoreCase("Customer")){
                    if (partes[4].equalsIgnoreCase("list")){
                        filterChain.doFilter(request, response);
                    }else
                        throw new TokenException("Token não autorizado!!");
                }else  
                    throw new TokenException("Token não autorizado!!");
            }
            case ("receipt") -> {
                if (usr.equalsIgnoreCase("Employee")){
                    filterChain.doFilter(request, response);
                } else  
                    throw new TokenException("Token não autorizado!!");
            }
            default -> {
                filterChain.doFilter(request, response);
            }
        }
    }
    
    
}
