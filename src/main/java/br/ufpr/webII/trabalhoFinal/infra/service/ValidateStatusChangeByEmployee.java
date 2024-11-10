package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;

public class ValidateStatusChangeByEmployee implements ValidateStatusChangeInterface {
    @Override
    public boolean validateStatusChange(RequestUpdateDTO data) {
        RequestStatusCategory currentStatus;
        RequestStatusCategory nextStatus;

        try {
            currentStatus = RequestStatusCategory.valueOf(data.currentStatus());
            nextStatus = RequestStatusCategory.valueOf(data.nextStatus());
        } catch (IllegalArgumentException e) {
            return false; // Status inválido
        }

        switch (currentStatus) {
            case BUDGETED:
                // O funcionário pode aprovar ou rejeitar a solicitação
                if (nextStatus == RequestStatusCategory.APPROVED) {
                    return true;
                }
                if (nextStatus == RequestStatusCategory.REJECTED) {
                    // Motivo da rejeição é obrigatório
                    return data.rejectionReason() != null && !data.rejectionReason().isEmpty();
                }
                return false;

            case REJECTED:
                // O funcionário pode aprovar a solicitação rejeitada
                if (nextStatus == RequestStatusCategory.APPROVED) {
                    return true;
                }
                return false;

            case FIXED:
                // O funcionário pode marcar como pago
                if (nextStatus == RequestStatusCategory.PAID) {
                    return true;
                }
                return false;

            case PAID:
                // O funcionário pode fechar a requisição após o pagamento
                if (nextStatus == RequestStatusCategory.FINALIZED) {
                    return true;
                }
                return false;

            default:
                return false; // Transições inválidas
        }
    }
}