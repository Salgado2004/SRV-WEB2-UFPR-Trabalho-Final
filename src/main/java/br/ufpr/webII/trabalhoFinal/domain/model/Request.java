package br.ufpr.webII.trabalhoFinal.domain.model;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Request {
    private @Id
    @GeneratedValue Long id;
    private String equipmentDesc;
    private EquipmentCategory equipmentCategory;
    private String defectDesc;
    private double budget;
    private String repairDesc;
    private String customerOrientations;
    private Customer customer;
    private ArrayList<RequestStatus> requestStatus;

    public Request() {
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

    public Request(RequestUpdateDTO request) {
        this.id = request.requestId();
        this.budget = request.budget();
        this.repairDesc = request.repairDesc();
        this.customerOrientations = request.customerOrientations();
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
