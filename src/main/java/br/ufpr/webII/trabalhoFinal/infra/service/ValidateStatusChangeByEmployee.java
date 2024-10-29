package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;

public class ValidateStatusChangeByEmployee implements ValidateStatusChangeInterface {
    @Override
    public boolean validateStatusChange(RequestUpdateDTO data) {
        // adicionar a lógica de validação de mudança de status por funcionário
        // pode usar como base a validação de cliente em ValidateStatusChangeByClient
        return true;
    }
    
}
