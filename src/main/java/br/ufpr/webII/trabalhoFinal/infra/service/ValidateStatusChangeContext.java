package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;

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