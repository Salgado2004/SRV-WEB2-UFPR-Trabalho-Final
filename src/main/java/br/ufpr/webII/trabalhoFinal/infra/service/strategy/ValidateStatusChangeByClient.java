package br.ufpr.webII.trabalhoFinal.infra.service.strategy;

import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;

public class ValidateStatusChangeByClient implements ValidateStatusChangeInterface {
    @Override
    public boolean validateStatusChange(RequestUpdateDTO data) {
        RequestStatusCategory currentStatus;
        RequestStatusCategory nextStatus;

        try {
            currentStatus = data.currentStatus();
            nextStatus = data.nextStatus();
        } catch (IllegalArgumentException e) {
            return false;
        }

        return switch (currentStatus) {
            case BUDGETED -> // O status BUDGETED só pode ser alterado para APPROVED ou REJECTED (com motivo)
                    nextStatus == RequestStatusCategory.APPROVED ||
                    (nextStatus == RequestStatusCategory.REJECTED && data.rejectReason() != null && !data.rejectReason().isEmpty());
            case REJECTED -> // O status REJECTED só pode ser alterado para APPROVED
                    nextStatus == RequestStatusCategory.APPROVED;
            case FIXED -> // O status FIXED só pode ser alterado para PAID
                nextStatus == RequestStatusCategory.PAID;
            default -> false;
        };
    }
}