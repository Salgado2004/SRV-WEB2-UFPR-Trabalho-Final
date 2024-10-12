package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.model.Employee;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RegisteringException;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufpr.webII.trabalhoFinal.domain.dto.EmployeeInputDTO;
import br.ufpr.webII.trabalhoFinal.infra.repository.EmployeeDAO;


@Service
public class EmployeeCrud {

  @Autowired
  private EmployeeDAO employeeDao;


  public Employee registerEmployee(EmployeeInputDTO employeeInputDTO){
    Employee employee = new Employee();

    
    
     try {
            employeeDao.insert(employee);
        } catch (IOException e){
            throw new RegisteringException("Erro ao salvar empregado.", e);
        }
    
    return employee;
  }
  
 
  
}
