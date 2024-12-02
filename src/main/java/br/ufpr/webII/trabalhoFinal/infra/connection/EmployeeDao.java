/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;

/**
 *
 * @author mateus
 */
public interface EmployeeDao extends DAO<Employee> {
    Employee getById(Long id) throws Exception;
}
