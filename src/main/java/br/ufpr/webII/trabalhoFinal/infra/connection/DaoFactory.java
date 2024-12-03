/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection;

import br.ufpr.webII.trabalhoFinal.infra.connection.json.*;
import br.ufpr.webII.trabalhoFinal.infra.connection.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author mateus
 */

@Service
public class DaoFactory {

    @Value("${dao.type}")
    private DaoType type;

    @Autowired
    JsonFileWriter jsonFileWriter;

    @Autowired
    ConnectionFactory connectionFactory;

    public UserDao getUserDao(){
        return switch (type) {
            case JSON -> UserJsonDao.getUserJsonDao(jsonFileWriter);
            case POSTGRES -> UserSQLDao.getUserSQLDao(connectionFactory);
            default -> throw new RuntimeException("Tipo não existe:" + type);
        };
    }
    
    public EmployeeDao getEmployeeDao(){
        return switch (type) {
            case JSON -> EmployeeJsonDao.getEmployeeJsonDao(jsonFileWriter);
            case POSTGRES -> EmployeeSQLDao.getEmployeeSQLDao(connectionFactory);
            default -> throw new RuntimeException("Tipo não existe:" + type);
        };
    }
    
    public CustomerDao getCustomerDao(){
        return switch (type) {
            case JSON -> CustomerJsonDao.getCustomerJsonDao(jsonFileWriter);
            case POSTGRES -> CustomerSQLDao.getCustomerSQLDao(connectionFactory);
            default -> throw new RuntimeException("Tipo não existe:" + type);
        };
    }
    
    public RequestDao getRequestDao(){
        return switch (type) {
            case JSON -> RequestJsonDao.getRequestJsonDao(jsonFileWriter);
            case POSTGRES -> RequestSQLDao.getRequestSQLDao(connectionFactory);
            default -> throw new RuntimeException("Tipo não existe:" + type);
        };
    }

    public EquipmentDao getEquipmentDao(){
        return switch (type) {
            case JSON -> EquipmentJsonDao.getEquipmentJsonDao(jsonFileWriter);
            case POSTGRES -> EquipmentSQLDao.getEquipmentSQLDao(connectionFactory);
            default -> throw new RuntimeException("Tipo não existe:" + type);
        };
    }
}
