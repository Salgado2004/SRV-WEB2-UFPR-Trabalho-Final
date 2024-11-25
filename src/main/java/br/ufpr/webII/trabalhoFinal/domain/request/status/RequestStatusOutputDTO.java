package br.ufpr.webII.trabalhoFinal.domain.request.status;

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
    public RequestStatusOutputDTO(
            Long requestId,
            Date dateTime,
            Long inChargeEmployeeId,
            Long senderEmployeeId,
            RequestStatusCategory category)
    {
        this(null, requestId, dateTime, inChargeEmployeeId, senderEmployeeId, category);
    }

    public RequestStatusOutputDTO(RequestStatus requestStatus, Long requestId) {
        this(
                requestStatus.getRequestStatusId(),
                requestId,
                Date.from(requestStatus.getDateTime().atZone(ZoneId.systemDefault()).toInstant()),
                requestStatus.getInChargeEmployee() == null ? null : requestStatus.getInChargeEmployee().getId(),
                requestStatus.getSenderEmployee() == null ? null : requestStatus.getSenderEmployee().getId(),
                requestStatus.getCategory());
    }
}
