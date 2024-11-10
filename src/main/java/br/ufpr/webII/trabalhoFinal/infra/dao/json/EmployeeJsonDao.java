/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.dao.json;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.infra.dao.EmployeeDao;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mateus
 */

@Service
public class EmployeeJsonDao implements EmployeeDao {
    
    private static EmployeeJsonDao employeeDao;
    
    private EmployeeJsonDao(){}

    public static EmployeeDao getEmployeeJsonDao() {
        if(employeeDao == null)
            return employeeDao = new EmployeeJsonDao();
        else
            return employeeDao;
    }
    
    @Autowired
    JsonFileService jsonService;

    @Override
    public void insert(Employee element) throws Exception {
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            long id = (data.size()+1);
            element.setId(id);
            data.add(new EmployeeOutputDTO(element));
            jsonService.writeJsonToFile("employees.json", data);
        } catch (IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }    }

    @Override
    public void update(Employee objeto) throws Exception {
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            for(EmployeeOutputDTO emp:data){
                if(Objects.equals(objeto.getId(), emp.id())){
                    data.remove(emp);
                    data.add(new EmployeeOutputDTO(objeto));
                    jsonService.writeJsonToFile("employees.json", data);

                }
            }
            
        }catch(IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }
    }

    @Override
    public void delete(Employee objeto) throws Exception {
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            for(EmployeeOutputDTO emp:data){
                if(emp.id() == objeto.getId()){
                    if(Employee.whoIsLoggedIn() == objeto.getId()){
                        System.out.println("O empregado não pode se deletar, sim somos anti-suicidio!");//Função demtro do empregado que vai validar se é ele logado
                        return;                     //pelas regras de negocio, o funcionario não pode se deletar
                    }else{
                        data.remove(emp);
                        jsonService.writeJsonToFile("employees.json", data);

                        return;
                    }
                }
                System.out.println("Empregado não encontrado.");
            }
        } catch (IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }
    }

    @Override
    public List<Employee> listAll() throws Exception {
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            ArrayList<Employee> employees = new ArrayList<>();
            for (EmployeeOutputDTO emp : data) {
                Employee newEmployee = new Employee(emp);
                employees.add(newEmployee);
            }
            return employees;
        } catch(IOException e){
            System.out.println("Erro ao abrir arquivos: "+e.getMessage());
        }
        return null;
    }
    
}
