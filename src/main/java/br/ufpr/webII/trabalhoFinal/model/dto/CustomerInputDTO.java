package br.ufpr.webII.trabalhoFinal.model.dto;

import br.ufpr.webII.trabalhoFinal.model.Address;

public record CustomerInputDTO(
        String cpf,
        String name,
        String surname,
        String email,
        Address address,
        String phone) {
}
