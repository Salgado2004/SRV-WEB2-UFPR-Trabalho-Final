package br.ufpr.webII.trabalhoFinal.infra.service.strategy;

import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;

public class ValidateStatusChangeContext {
    private ValidateStatusChangeInterface strategy;

    public ValidateStatusChangeContext() {
    }

    public void setStrategy(ValidateStatusChangeInterface strategy) {
        this.strategy = strategy;
    }

    public boolean isValid(RequestUpdateDTO data) {
        return strategy.validateStatusChange(data);
    }
}