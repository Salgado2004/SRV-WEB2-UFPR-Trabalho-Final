package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.Address;
import br.ufpr.webII.trabalhoFinal.domain.model.Customer;

public record CustomerOutputDTO(
        Long id,
        String cpf,
        String name,
        String surname,
        String email,
        Address address,
        String phone,
        String password){

    public CustomerOutputDTO(Customer c){
        this(c.getId(), c.getCpf(), c.getName(), c.getSurname(), c.getEmail(), c.getAddress(), c.getPhone(), c.getPassword()+":"+c.getSalt());
    }
}
