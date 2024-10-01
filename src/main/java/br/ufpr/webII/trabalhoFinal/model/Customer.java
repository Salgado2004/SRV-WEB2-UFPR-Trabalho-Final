package br.ufpr.webII.trabalhoFinal.model;

public class Customer extends User{

    private String cpf;
    private Address address;

    public Customer() {
    }

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

}
