/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.dao;

import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mateus
 */
public class CustomerJsonDao implements ClientDao {
    
    public static CustomerJsonDao customerDao;
     
    @Autowired
    JsonFileService jsonService;
    
    private CustomerJsonDao(){
    }

    static ClientDao getCustomerJsonDao() {
        if(customerDao == null){
            return customerDao = new CustomerJsonDao();
        } else{
            return customerDao;
        }
    }

    @Override
    public void insert(Customer element) throws Exception {
         try{
            List<CustomerOutputDTO> data = jsonService.readObjectFromFile("clients.json", new TypeReference<>() {});
            long id = (data.size()+1);
            element.setId(id);
            data.add(new CustomerOutputDTO(element));
            jsonService.writeJsonToFile("employees.json", data);
        } catch (IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }    
    }

    @Override
    public void update(Customer objeto) throws Exception {
        try{
            List<CustomerOutputDTO> data = jsonService.readObjectFromFile("clients.json", new TypeReference<>() {});
            for(CustomerOutputDTO emp:data){
                if(Objects.equals(objeto.getId(), emp.id())){
                    data.remove(emp);
                    data.add(new CustomerOutputDTO(objeto));
                    jsonService.writeJsonToFile("clients.json", data);
                }
            }
        }catch(IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }    
    }

    @Override
    public void delete(Customer objeto) throws Exception {
        try{
            List<CustomerOutputDTO> data = jsonService.readObjectFromFile("clients.json", new TypeReference<>() {});
            for(CustomerOutputDTO emp:data){
                if(emp.id() == objeto.getId()){
                        data.remove(emp);
                        jsonService.writeJsonToFile("clients.json", data);
                        return;
                }
                System.out.println("Cliente não encontrado.");
            }
        } catch (IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }    
    }

    @Override
    public List<Customer> listAll() throws Exception {
        try{
            List<CustomerOutputDTO> data = jsonService.readObjectFromFile("clients.json", new TypeReference<>() {});
            ArrayList<Customer> customers = new ArrayList<>();
            for (CustomerOutputDTO emp : data) {
                Customer newEmployee = new Customer(emp);
                customers.add(newEmployee);
            }
            return customers;
        } catch(IOException e){
            System.out.println("Erro ao abrir arquivos: "+e.getMessage());
        }
        return null;
    }

    Customer selectCustomer(Long customerId) {
        try {
            List<CustomerOutputDTO> customers = jsonService.readObjectFromFile("clients.json", new TypeReference<>(){});
            for (CustomerOutputDTO customer : customers) {
                if (customer.id().equals(customerId)) {
                    return new Customer(customer);
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao consultar arquivos: "+ e.getMessage());
        }
        throw new ResourceNotFoundException("Cliente não encontrado");
    }
    
}
