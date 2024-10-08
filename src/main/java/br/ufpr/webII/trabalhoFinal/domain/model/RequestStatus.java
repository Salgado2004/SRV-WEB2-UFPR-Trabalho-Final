package br.ufpr.webII.trabalhoFinal.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class RequestStatus {

    private @Id
    @GeneratedValue Long requestStatusId;
    private Long requestId;
    private LocalDateTime dateTime;
    private Long inChargeEmployeeId;
    private Long senderEmployeeId;
    private String category;

    public RequestStatus() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(Long requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getInChargeEmployeeId() {
        return inChargeEmployeeId;
    }

    public void setInChargeEmployeeId(Long inChargeEmployeeId) {
        this.inChargeEmployeeId = inChargeEmployeeId;
    }

    public Long getSenderEmployeeId() {
        return senderEmployeeId;
    }

    public void setSenderEmployeeId(Long senderEmployeeId) {
        this.senderEmployeeId = senderEmployeeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestStatus that = (RequestStatus) o;
        return Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }
}
