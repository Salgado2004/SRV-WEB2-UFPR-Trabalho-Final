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

        String authorizationHeader = getAuthorizationToken(request);
        String autorizationURL = request.getRequestURI();
       
        String[] partes = new String[7];
        partes = autorizationURL.split("/");
        
        switch (partes[3]){ //o switch case foi alterado para o rule switch por recomendação da IDE. sintaxe está correta.
            case ("employee") -> {
                if (tokenSrv.getProfile(authorizationHeader).equalsIgnoreCase("Employee")){
                    /*follow your life :D*/
                    filterChain.doFilter(request, response);
                } else  
                    throw new TokenException("Token não autorizado!!");
            }
            case ("customer") -> {
                if (tokenSrv.getProfile(authorizationHeader).equalsIgnoreCase("Customer")){
                    filterChain.doFilter(request, response);
                } else
                    throw new TokenException("Token não autorizado!!");
            }
            case ("requests") -> {
                if (tokenSrv.getProfile(authorizationHeader).equalsIgnoreCase("Employee")){
                    if (request.getMethod().equals("POST"))
                        throw new TokenException("Um empregado não pode criar um request, se registre como cliente e tente novamente.");
                    filterChain.doFilter(request, response);
                } else
                    filterChain.doFilter(request, response);
            }
            case ("equipment-category") -> {
                if (tokenSrv.getProfile(authorizationHeader).equalsIgnoreCase("Employee")){
                    filterChain.doFilter(request, response);
                } else if(tokenSrv.getProfile(authorizationHeader).equalsIgnoreCase("Customer")){
                    if (request.getMethod().equals("GET")){
                        filterChain.doFilter(request, response);
                    }else
                        throw new TokenException("Token não autorizado!!");
                }else  
                    throw new TokenException("Token não autorizado!!");
            }
            case ("receipt") -> {
                if (tokenSrv.getProfile(authorizationHeader).equalsIgnoreCase("Employee")){
                    filterChain.doFilter(request, response);
                } else  
                    throw new TokenException("Token não autorizado!!");
            }
            default -> {
                filterChain.doFilter(request, response);
            }
        }
    }

    private String getAuthorizationToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null)
            return "";
        return authorizationHeader.replace("Bearer ", "");
    }
    
}
