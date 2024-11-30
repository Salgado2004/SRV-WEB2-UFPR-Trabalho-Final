/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateus
 */
public class PresentRequestToUserContext {
    
    
    private final String client = "SELECT * FROM public.request WHERE active = true AND customer_id = ?";
    private final String employee = "SELECT * FROM public.request AS request INNER JOIN public.request_status AS status "
            + "ON request.request_id = status.request_id"
            + "WHERE status.in_charge_employee = ? OR status.status = OPEN";
    
    public PresentRequestToUserContext(){
    }
    
    public List<Request> showRequestToUser(JWTToken token){
        List<Request> listagem = new ArrayList<Request>();
        try(//implementar conexão depois ou fazer a chamda do dau passando a string
                ){
            
        }
        
        return listagem;
    }
    
}
