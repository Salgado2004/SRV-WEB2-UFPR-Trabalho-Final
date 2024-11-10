package br.ufpr.webII.trabalhoFinal.domain.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MessageDTO (

    @NotBlank
    String owner,

    @NotBlank
    @Email
    String from,

    @NotBlank
    @Email
    String to,

    @NotBlank
    String title,

    @NotBlank
    String text
){
    public MessageDTO(String owner, String to){
        this(owner, "manutads.noreply@gmail.com", to, "Assunto", "Texto do email");
    }
}
