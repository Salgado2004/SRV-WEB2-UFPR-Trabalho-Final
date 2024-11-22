package br.ufpr.webII.trabalhoFinal.domain.request.status;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeOutputDTO;

import java.time.ZoneId;
import java.util.Date;

public record RequestStatusOutputDTO(
        Long requestStatusId,
        Long requestId,
        Date dateTime,
        EmployeeOutputDTO inChargeEmployee,
        EmployeeOutputDTO senderEmployee,
        RequestStatusCategory category) {
    public RequestStatusOutputDTO(RequestStatus requestStatus){
        this(
                requestStatus.getRequestStatusId(),
                requestStatus.getRequest().getId(),
                Date.from(requestStatus.getDateTime().atZone(ZoneId.systemDefault()).toInstant()),
                requestStatus.getInChargeEmployee() == null ? null : new EmployeeOutputDTO(requestStatus.getInChargeEmployee()),
                requestStatus.getSenderEmployee() == null ? null : new EmployeeOutputDTO(requestStatus.getSenderEmployee()),
                requestStatus.getCategory());
    }
    public RequestStatusOutputDTO(
            Long requestId,
            Date dateTime,
            Employee inChargeEmployee,
            Employee senderEmployee,
            RequestStatusCategory category)
    {
        this(null, requestId, dateTime, new EmployeeOutputDTO(inChargeEmployee), new EmployeeOutputDTO(senderEmployee), category);
    }

    public RequestStatusOutputDTO(RequestStatus requestStatus, Long requestId) {
        this(
                requestStatus.getRequestStatusId(),
                requestId,
                Date.from(requestStatus.getDateTime().atZone(ZoneId.systemDefault()).toInstant()),
                requestStatus.getInChargeEmployee() == null ? null : new EmployeeOutputDTO(requestStatus.getInChargeEmployee()),
                requestStatus.getSenderEmployee() == null ? null : new EmployeeOutputDTO(requestStatus.getSenderEmployee()),
                requestStatus.getCategory());
    }
}
