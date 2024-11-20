package br.ufpr.webII.trabalhoFinal.domain.request.status;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class RequestStatus {

    private Long requestStatusId;
    private Request request;
    private LocalDateTime dateTime;
    private Employee inChargeEmployee;
    private Employee senderEmployee;
    private RequestStatusCategory category;

    public RequestStatus() {
    }

    public RequestStatus(Request request,RequestStatusCategory category, Employee inChargeEmployee, Employee senderEmployee, LocalDateTime dateTime) {
        this.request = request;
        this.category = category;
        this.inChargeEmployee = inChargeEmployee;
        this.senderEmployee = senderEmployee;
        this.dateTime = dateTime;
    }

    public RequestStatus(Request request,RequestStatusCategory category,  LocalDateTime dateTime) {
        this.request = request;
        this.category = category;
        this.dateTime = dateTime;
    }

    public RequestStatus(Request newRequest, RequestStatusOutputDTO requestStatus) {
        this.request = newRequest;
        this.dateTime = requestStatus.dateTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        this.category = requestStatus.category();
    }

    public Long getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(Long requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Employee getInChargeEmployee() {
        return inChargeEmployee;
    }

    public void setInChargeEmployee(Employee inChargeEmployee) {
        this.inChargeEmployee = inChargeEmployee;
    }

    public Employee getSenderEmployee() {
        return senderEmployee;
    }

    public void setSenderEmployee(Employee senderEmployee) {
        this.senderEmployee = senderEmployee;
    }

    public RequestStatusCategory getCategory() {
        return category;
    }

    public void setCategory(RequestStatusCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestStatus that = (RequestStatus) o;
        return Objects.equals(requestStatusId, that.requestStatusId) && Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestStatusId, request);
    }
}
