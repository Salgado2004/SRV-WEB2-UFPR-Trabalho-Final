package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.Address;

public record CustomerInputDTO(
        String cpf,
        String name,
        String surname,
        String email,
        Address address,
        String phone) {
}
