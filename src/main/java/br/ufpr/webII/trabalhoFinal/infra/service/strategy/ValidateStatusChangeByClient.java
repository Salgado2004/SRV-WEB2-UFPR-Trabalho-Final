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

        switch (currentStatus) {
            case BUDGETED:
                if (nextStatus == RequestStatusCategory.APPROVED) {
                    return true;
                }

                if (nextStatus == RequestStatusCategory.REJECTED) {
                    if (data.rejectionReason() != null && !data.rejectionReason().isEmpty()) {
                        return true;
                    }
                }
                return false;

            case REJECTED:
                if (nextStatus == RequestStatusCategory.APPROVED) {
                    return true;
                }
                return false;

            case FIXED:
                if (nextStatus == RequestStatusCategory.PAID) {
                    return true;
                }
                return false;
            
            default:
                return false;
        }
    }
}