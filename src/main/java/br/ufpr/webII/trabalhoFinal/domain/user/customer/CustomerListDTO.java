package br.ufpr.webII.trabalhoFinal.domain.user.customer;

import br.ufpr.webII.trabalhoFinal.domain.address.AddressDTO;

public record CustomerListDTO(
        Long id,
        String name,
        String surname,
        String email,
        String phone,
        String password,
        String cpf,
        AddressDTO address) {
    public CustomerListDTO(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getPassword(),
                customer.getCpf(),
                new AddressDTO(customer.getAddress())
        );
    }
}