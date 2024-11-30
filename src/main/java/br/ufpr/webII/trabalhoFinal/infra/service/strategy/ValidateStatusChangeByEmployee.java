package br.ufpr.webII.trabalhoFinal.infra.service.strategy;

import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;

public class ValidateStatusChangeByEmployee implements ValidateStatusChangeInterface {
    @Override
    public boolean validateStatusChange(RequestUpdateDTO data) {
        RequestStatusCategory currentStatus;
        RequestStatusCategory nextStatus;

        try {
            currentStatus = data.currentStatus();
            nextStatus = data.nextStatus();
        } catch (IllegalArgumentException e) {
            return false; // Status inválido
        }

        return switch (currentStatus) {
            case OPEN -> nextStatus == RequestStatusCategory.BUDGETED;
            case APPROVED -> nextStatus == RequestStatusCategory.REDIRECTED || nextStatus == RequestStatusCategory.FIXED;
            case PAID -> nextStatus == RequestStatusCategory.FINALIZED;
            default -> false; // Transições inválidas
        };
    }
}