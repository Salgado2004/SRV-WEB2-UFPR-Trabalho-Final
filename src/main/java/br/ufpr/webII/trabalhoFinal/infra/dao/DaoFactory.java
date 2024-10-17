/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.dao;

/**
 *
 * @author mateus
 */
public class DaoFactory {
    
    private DaoFactory(){}
    
    public static EmployeeDao getEmployeeDao(DaoType type){
        switch(type){
            case JSON_EMPLOYEE:
                return EmployeeJsonDao.getEmployeeJsonDao();
            case SQL_EMPLOYEE:
                return EmployeeSQLDao.getEmployeeSQLDao();
            default:
                throw new RuntimeException("Tipo não existe:"+type);
        }
    }
    
    public static ClientDao getClientDao(DaoType type){
        switch(type){
            case JSON_CUSTOMER:
                return CustomerJsonDao.getCustomerJsonDao();
            case SQL_CUSTOMER:
                return CustomerSQLDao.getCustomerSQLDao();
            default:
                throw new RuntimeException("Tipo não existe:"+type);
        }
    }
    
    public static RequestDao getRequestDao(DaoType type){
        switch(type){
            case JSON_REQUEST:
                return RequestJsonDao.getRequestJsonDao();
            case SQL_REQUEST:
                return RequestSQLDao.getRequestSQLDao();
            default:
                throw new RuntimeException("Tipo não existe:"+type);

        }
    }
}
