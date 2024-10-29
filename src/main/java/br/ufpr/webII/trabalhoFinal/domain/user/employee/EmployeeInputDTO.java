/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.domain.user.employee;

import jakarta.validation.constraints.NotBlank;
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
        @Size (max = 50)
        String email,
        
        @NotBlank
        String telefone
               
        ) {
    
    

}
