/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;

import br.ufpr.webII.trabalhoFinal.infra.exceptions.TokenException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mateus
 */
public class PresentRequestToUserContext {
    
    @Autowired
    TokenService tokenSrv;
    

        
    private String client = "SELECT * FROM public.request WHERE active = true AND customer_id = ";
    private String employee = "SELECT * FROM public.request AS request INNER JOIN public.request_status AS status "
            + "ON request.request_id = status.request_id"
            + "WHERE status.status = OPEN OR status.in_charge_employee = ";
    
    public PresentRequestToUserContext(){
    }
    
    public String showRequestToUser(){
        String sql = "";
        String tipo = tokenSrv.getProfile(token);
        
        switch(tipo){
            case "employee":
                employee += tokenSrv.getUserId(token);
                sql = employee;
                break;
            case "customer":
                client += tokenSrv.getUserId(token);
                sql = client;
                break;
            default:
                throw new TokenException("Token não autorizado!");
        }

        return sql;
    }
    
}
