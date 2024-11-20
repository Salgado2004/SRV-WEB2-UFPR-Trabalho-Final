/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection;

import java.sql.Timestamp;
import java.util.ArrayList;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CategoryReport;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CommomReport;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;

/**
 *
 * @author mateus
 */
public abstract class RequestDao implements DAO<Request> {
    
    public abstract Request getById(Long id) throws Exception;
    public abstract void insertStatus(RequestStatus requestStatus) throws Exception;
    public abstract void requestUpdate(RequestUpdateDTO requestStatus) throws Exception;
    public ArrayList<CommomReport> listCommomReport(Timestamp startDate, Timestamp endDate) throws Exception {
        return new ArrayList<>();
    };
    public ArrayList<CategoryReport> listCategoryReport() throws Exception {
        return new ArrayList<>();
    };

}
