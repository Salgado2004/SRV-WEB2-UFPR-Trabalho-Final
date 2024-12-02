package br.ufpr.webII.trabalhoFinal.domain.request;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Request {
    private Long id;
    private String equipmentDesc;
    private EquipmentCategory equipmentCategory;
    private String defectDesc;
    private LocalDateTime createdAt;
    private double budget;
    private String repairDesc;
    private String rejectReason;
    private String customerOrientations;
    private Customer customer;
    private ArrayList<RequestStatus> requestStatus;
    private Boolean active;

    public Request(){
    }

    public Request(Long id) {
        this.id = id;
    }

    public Request(RequestInputDTO dto, Customer customer, EquipmentCategory equipment){
        this.equipmentDesc = dto.equipmentDesc();
        this.defectDesc = dto.defectDesc();
        this.equipmentCategory = equipment;
        this.customer = customer;
        this.requestStatus = new ArrayList<>();
        this.requestStatus.add(new RequestStatus(this, RequestStatusCategory.OPEN, dto.startDate()));
    }

    public Request(RequestOutputDTO request) {
        this.id = request.requestId();
        this.equipmentDesc = request.equipmentDesc();
        this.equipmentCategory = new EquipmentCategory();
        this.defectDesc = request.defectDesc();
        this.budget = request.budget();
        this.repairDesc = request.repairDesc();
        this.customerOrientations = request.customerOrientations();
        this.requestStatus = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentDesc() {
        return equipmentDesc;
    }

    public void setEquipmentDesc(String equipmentDesc) {
        this.equipmentDesc = equipmentDesc;
    }

    public EquipmentCategory getEquipmentCategory() {
        return equipmentCategory;
    }

    public void setEquipmentCategory(EquipmentCategory equipmentCategory) {
        this.equipmentCategory = equipmentCategory;
    }

    public String getDefectDesc() {
        return defectDesc;
    }

    public void setDefectDesc(String defectDesc) {
        this.defectDesc = defectDesc;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public String getRepairDesc() {
        return repairDesc;
    }

    public void setRepairDesc(String repairDesc) {
        this.repairDesc = repairDesc;
    }

    public String getCustomerOrientations() {
        return customerOrientations;
    }

    public void setCustomerOrientations(String customerOrientations) {
        this.customerOrientations = customerOrientations;
    }

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<RequestStatus> getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(ArrayList<RequestStatus> requestStatus) {
        this.requestStatus = requestStatus;
    }
    public void addRequestStatus(RequestStatus requestStatus) {
        this.requestStatus.add(requestStatus);
    }

    public Boolean getActive(){
        return this.active;
    }

    public void setActive(Boolean active){
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
