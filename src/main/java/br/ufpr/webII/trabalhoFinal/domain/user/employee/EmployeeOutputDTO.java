/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.domain.user.employee;

import java.util.Date;

/**
 *
 * @author mateus
 */
public record EmployeeOutputDTO(
        Long id,
        String name,
        String surname,
        String email,
        Date birthDate,
        String password
        ) {
        
    public EmployeeOutputDTO(Employee e){
        this(e.getId(),e.getName(),e.getSurname(),e.getEmail(),e.getBirthDate(),e.getPassword()+":"+e.getSalt());
    }

}
