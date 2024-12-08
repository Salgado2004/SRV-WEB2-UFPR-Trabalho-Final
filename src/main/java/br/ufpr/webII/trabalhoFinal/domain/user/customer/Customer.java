package br.ufpr.webII.trabalhoFinal.domain.user.customer;

import br.ufpr.webII.trabalhoFinal.domain.address.Address;
import br.ufpr.webII.trabalhoFinal.domain.user.User;

public class Customer extends User {
    private Long customerId;
    private String cpf;
    private Address address;
    private String phone;

    public Customer(){}

    public Customer(CustomerInputDTO dto){
        this.setCpf(dto.cpf().replaceAll("[^0-9]", ""));
        this.setName(dto.name());
        this.setSurname(dto.surname());
        this.setEmail(dto.email());
        this.setAddress(dto.address());
        this.setPhone(dto.phone().replaceAll("[^0-9]", ""));
    }

    public Customer(CustomerOutputDTO dto) {
        this.setId(dto.id());
        this.setCpf(dto.cpf());
        this.setName(dto.name());
        this.setSurname(dto.surname());
        this.setEmail(dto.email());
        this.setAddress(dto.address());
        this.setPhone(dto.phone());
        String[] auth = dto.password().split(":");
        this.setPassword(auth[0]);
        this.setSalt(auth[1]);
    }

    public Customer(long customerId) {
        this.setId(customerId);
    }

    public Customer(Long id, String name, String surname, String email, String phone, String cpf, Address address){
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
        this.setPhone(phone);
        this.setCpf(cpf);
        this.setAddress(address);
    }
    // Getters e Setters para todos os campos, incluindo os herdados de User

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}