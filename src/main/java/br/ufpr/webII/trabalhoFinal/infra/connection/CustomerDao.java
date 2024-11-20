/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection;

import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import jakarta.validation.constraints.NotNull;


/**
 *
 * @author mateus
 */
public interface CustomerDao extends DAO<Customer> {

    public Customer getByUserId(@NotNull Long aLong) throws Exception;
    public Customer getById(@NotNull Long aLong) throws Exception;
}
