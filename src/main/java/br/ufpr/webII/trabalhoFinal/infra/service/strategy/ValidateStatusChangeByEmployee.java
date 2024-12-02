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
            case OPEN -> // O status OPEN só pode ser alterado para BUDGETED
                    nextStatus == RequestStatusCategory.BUDGETED;
            case APPROVED, REDIRECTED -> // Os status APPROVED e REDIRECTED podem ser alterados para FIXED (com descrição de reparo) ou REDIRECTED (com funcionário)
                    (nextStatus == RequestStatusCategory.REDIRECTED && data.senderEmployeeId() != null) ||
                    (nextStatus == RequestStatusCategory.FIXED && data.repairDesc() != null && !data.repairDesc().isEmpty());
            case PAID -> // O status PAID só pode ser alterado para FINALIZED
                    nextStatus == RequestStatusCategory.FINALIZED;
            default -> false; // Transições inválidas
        };
    }
}