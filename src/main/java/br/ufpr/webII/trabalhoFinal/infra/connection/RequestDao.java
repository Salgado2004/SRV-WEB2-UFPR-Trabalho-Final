/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;

/**
 *
 * @author mateus
 */
public interface RequestDao extends DAO<Request> {
    
    public void insertStatus(RequestStatus requestStatus) throws Exception;
    
}
