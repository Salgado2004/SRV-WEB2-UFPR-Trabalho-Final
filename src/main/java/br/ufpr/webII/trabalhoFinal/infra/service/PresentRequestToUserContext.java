package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.infra.exceptions.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mateus
 */

@Component
public class PresentRequestToUserContext {

    public String showRequestToUser(String profile){
        return switch (profile) {
            case "Customer" -> "SELECT * FROM public.request WHERE customer_id = ? AND active = TRUE;";
            case "Employee" -> "SELECT * FROM public.request WHERE id IN (SELECT request_id FROM public.request_status rs WHERE (status = 'OPEN' OR in_charge_employee_id = ?) AND date_time = (SELECT MAX(date_time) FROM public.request_status WHERE request_id = rs.request_id)) AND active = TRUE;";
            default -> throw new TokenException("Perfil não encontrado");
        };
    }
    
}
