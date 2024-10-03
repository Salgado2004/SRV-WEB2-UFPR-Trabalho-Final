package br.ufpr.webII.trabalhoFinal.dto;

import br.ufpr.webII.trabalhoFinal.model.Address;

public class CustomerDTO {
    private String cpf; // CPF do cliente
    private String name; // Nome do cliente
    private String surname; // Sobrenome do cliente
    private String email; // E-mail do cliente
    private Address address; // Endereço do cliente
    private String phone; // Telefone do cliente

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
