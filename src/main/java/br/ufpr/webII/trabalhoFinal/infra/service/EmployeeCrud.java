package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RegisteringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufpr.webII.trabalhoFinal.infra.connection.sql.EmployeeSQLDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeCrud {

    @Autowired
    private EmployeeSQLDao employeeSQLDao;

    public void registerEmployee(EmployeeInputDTO data) {
        try {
            Employee employee = new Employee(data);
            String salt = PasswordService.generateSalt();
            String password = PasswordService.hashPassword(data.password(), salt);
            employee.setPassword(password);
            employeeSQLDao.insert(employee);
        } catch (Exception e){
            throw new RegisteringException("Erro ao inserir funcionário: " + e.getMessage(), e);
        }
    }

    public void deleteEmployee(EmployeeInputDTO data) {
        try{
            Employee employee = new Employee(data);
            employeeSQLDao.delete(employee);
        } catch (Exception e){
            throw new RegisteringException("Erro ao excluir o empregado: " + e.getMessage(), e);
        }
    }

    public void updateEmployee(EmployeeInputDTO data) {
        try{
            Employee employee = new Employee(data);
            employeeSQLDao.update(employee);
        } catch(Exception e){
            throw new RegisteringException("Erro ao atualizar o empregado: " + e.getMessage(), e);
        }
    }

    public List<EmployeeOutputDTO> listEmployees() {
        try{
            List<Employee> employees = employeeSQLDao.listAll();
            List<EmployeeOutputDTO> employeesOutputDTO = new ArrayList<>();
            for (Employee employee : employees) {
                EmployeeOutputDTO employeeOutputDTO = new EmployeeOutputDTO(employee);
                employeesOutputDTO.add(employeeOutputDTO);
            }
            return employeesOutputDTO;
        } catch (Exception e){
            throw new RegisteringException("Erro ao listar os empregados: " + e.getMessage(), e);
        }
    }
}
