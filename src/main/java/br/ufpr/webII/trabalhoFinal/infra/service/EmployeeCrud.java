package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.model.Employee;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RegisteringException;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufpr.webII.trabalhoFinal.domain.dto.EmployeeInputDTO;
import br.ufpr.webII.trabalhoFinal.infra.repository.EmployeeDAO;
import java.util.Random;


@Service
public class EmployeeCrud {

  @Autowired
  private EmployeeDAO employeeDao;


  public Employee registerEmployee(Employee employee){
    
     try {
            employeeDao.insert(employee);
        } catch (Exception e){
            System.out.println("Erro ao inserir um novo empregado: "+e.getMessage());
        }
    
    return employee;
  }
  
 public void isValidEmail(String email) {
        // Verifica se o e-mail não é nulo e se contém o símbolo "@" seguido de um domínio.
        if (email == null || !email.contains("@") || !email.matches("[^@\\s]+@[^@\\s]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?")){
            throw new RegisteringException("E-mail inválido");
        }
    }
 
 private String generateRandomPassword() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Senha de 4 dígitos
    }

    public void deleteEmployee(Employee emp) {
        try{
            employeeDao.delete(emp.getId());
        } catch (Exception e){
            System.out.println("Erro ao deletar o empregado: "+e.getMessage());
        }
    }

    public void updateEmployee(Employee emp) {
        try{
            employeeDao.update(emp);
        } catch(Exception e){
            System.out.println("Erro ao atualizar o emptrgado: "+e.getMessage());
        }
    }
  
    
}
