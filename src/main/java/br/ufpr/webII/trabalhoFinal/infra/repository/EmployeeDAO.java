/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.dto.EmployeeOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.model.Employee;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mateus
 */
@Component
public class EmployeeDAO {
    
    @Autowired
    JsonFileService jsonService;
    
    public void insert(Employee employee){
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            long id = (data.size()+1);
            employee.setId(id);
            data.add(new EmployeeOutputDTO(employee));
            jsonService.writeJsonToFile("employees.json", data);
        } catch (IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }
    }


    public void delete(long id){
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            for(EmployeeOutputDTO emp:data){
                if(emp.id() == id){
                    //Bloco comentado enquanto o IsLoggedIn não é implementado
                    /*
                    if(Employee.isLoggedIn() == id){
                        System.out.println("O empregado não pode se deletar, sim somos anti-suicidio!");//Função demtro do empregado que vai validar se é ele logado
                        return;                     //pelas regras de negocio, o funcionario não pode se deletar
                    }else{
                        data.remove(emp);
                        jsonService.writeJsonToFile("employees.json", data);

                        return;
                    }*/
                }
                System.out.println("Empregado não encontrado.");
            }
        } catch (IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }
    }

    public void update(Employee employee){
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            for(EmployeeOutputDTO emp:data){
                if(Objects.equals(employee.getId(), emp.id())){
                    data.remove(emp);
                    data.add(new EmployeeOutputDTO(employee));
                    jsonService.writeJsonToFile("employees.json", data);

                }
            }
            
        }catch(IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }
    }
    
    public void listAll(){
        try{
            List<EmployeeOutputDTO> data = jsonService.readObjectFromFile("employees.json", new TypeReference<>() {});
            for (EmployeeOutputDTO emp: data){
                //Entrega cada elemento do data, to cansadito depois eu implemento
            }
        } catch(IOException e){
            System.out.println("Erro ao abrir arquivos: "+e.getMessage());
        }
    }
    
}
