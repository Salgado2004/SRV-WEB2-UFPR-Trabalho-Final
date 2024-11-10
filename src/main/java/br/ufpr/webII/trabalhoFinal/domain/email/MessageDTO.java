    package br.ufpr.webII.trabalhoFinal.domain.email;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class MessageDTO {

    @NotBlank
    private String owner;

    @NotBlank
    @Email
    private String from;

    @NotBlank
    @Email
    private String to;

    @NotBlank
    private String title;

    @NotBlank
    private String text;
}
