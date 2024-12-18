/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection.json;

import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.infra.connection.CustomerDao;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.connection.JsonFileWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mateus
 */
public class CustomerJsonDao implements CustomerDao {
    
    public static CustomerJsonDao customerDao;

    JsonFileWriter jsonService;
    
    CustomerJsonDao(JsonFileWriter jsonService){
        this.jsonService = jsonService;
    }

    public static CustomerDao getCustomerJsonDao(JsonFileWriter jsonFileWriter) {
        if(customerDao == null){
            return customerDao = new CustomerJsonDao(jsonFileWriter);
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
            jsonService.writeJsonToFile("clients.json", data);
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
                if(Objects.equals(emp.id(), objeto.getId())){
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

    @Override
    public Customer getByUserId(Long customerId) {
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

    @Override
    public Customer getById(Long customerId) {
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
