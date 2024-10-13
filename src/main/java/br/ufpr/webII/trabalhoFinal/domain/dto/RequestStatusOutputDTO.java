package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.model.RequestStatusCategory;

import java.time.ZoneId;
import java.util.Date;

public record RequestStatusOutputDTO(
        Long requestStatusId,
        Long requestId,
        Date dateTime,
        Long inChargeEmployeeId,
        Long senderEmployeeId,
        RequestStatusCategory category) {
    public RequestStatusOutputDTO(RequestStatus requestStatus){
        this(
                requestStatus.getRequestStatusId(),
                requestStatus.getRequest().getId(),
                Date.from(requestStatus.getDateTime().atZone(ZoneId.systemDefault()).toInstant()),
                requestStatus.getInChargeEmployee() == null ? null : requestStatus.getInChargeEmployee().getId(),
                requestStatus.getSenderEmployee() == null ? null : requestStatus.getSenderEmployee().getId(),
                requestStatus.getCategory());
    }
}