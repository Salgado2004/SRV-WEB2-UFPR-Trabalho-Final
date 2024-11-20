/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.domain.user.employee;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author mateus
 */
public record EmployeeInputDTO(
        
        @NotBlank
        @Size (max = 255)
        String name,

        @NotBlank
        String surname,
        
        @NotBlank
        @Size (max = 50)
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        Date birthDate
        ) {}
