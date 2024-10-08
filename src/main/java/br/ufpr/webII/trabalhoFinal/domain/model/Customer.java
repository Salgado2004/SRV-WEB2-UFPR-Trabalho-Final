package br.ufpr.webII.trabalhoFinal.domain.model;

import br.ufpr.webII.trabalhoFinal.domain.dto.CustomerInputDTO;

public class Customer extends User {
    private String cpf;
    private Address address;
    private String phone;

    public Customer(){}

    public Customer(CustomerInputDTO dto){
        this.setCpf(dto.cpf());
        this.setName(dto.name());
        this.setSurname(dto.surname());
        this.setEmail(dto.email());
        this.setAddress(dto.address());
        this.setPhone(dto.phone());
    }

    // Getters e Setters para todos os campos, incluindo os herdados de User
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}