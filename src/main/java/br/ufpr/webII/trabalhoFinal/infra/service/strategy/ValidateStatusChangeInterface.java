package br.ufpr.webII.trabalhoFinal.infra.service.strategy;

import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;

public interface ValidateStatusChangeInterface {
    boolean validateStatusChange(RequestUpdateDTO data);
}