package br.ufpr.webII.trabalhoFinal.infra.service.strategy;

import br.ufpr.webII.trabalhoFinal.infra.exceptions.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mateus
 */

@Component
public class PresentRequestToUserContext {

    public String showRequestToUser(String profile) {
        return switch (profile) {
            case "Customer" -> "SELECT * FROM proc_customerList(?) order by created_at;";
            case "Employee" -> "SELECT * FROM proc_employeeList(?) order by created_at;";
            default -> throw new TokenException("Perfil não encontrado");
        };
    }

}
