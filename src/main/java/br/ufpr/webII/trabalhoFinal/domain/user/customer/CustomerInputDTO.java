package br.ufpr.webII.trabalhoFinal.domain.user.customer;

import br.ufpr.webII.trabalhoFinal.domain.address.Address;
import jakarta.validation.constraints.NotBlank;

public record CustomerInputDTO(
        @NotBlank
        String cpf,
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        String email,
        Address address,
        String phone) {
}
