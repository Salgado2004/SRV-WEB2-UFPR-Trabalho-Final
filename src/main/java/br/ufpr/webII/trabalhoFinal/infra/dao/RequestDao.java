/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.dao;

import br.ufpr.webII.trabalhoFinal.domain.model.Request;
import br.ufpr.webII.trabalhoFinal.domain.model.RequestStatus;

/**
 *
 * @author mateus
 */
public interface RequestDao extends DAO<Request> {
    
    public void insertStatus(RequestStatus requestStatus) throws Exception;
    
}