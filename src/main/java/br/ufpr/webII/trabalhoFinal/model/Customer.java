package br.ufpr.webII.trabalhoFinal.model;

public class Customer extends User {
    private String cpf;
    private Address address;
    private String phone;

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