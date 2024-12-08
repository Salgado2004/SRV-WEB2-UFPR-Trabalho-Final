package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RegisteringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufpr.webII.trabalhoFinal.infra.connection.DaoFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.EmployeeDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeCrud {
    @Autowired
    private DaoFactory daoFactory;

    @Autowired
    private TokenService tokenSrv;

    public void registerEmployee(EmployeeInputDTO data) {
        try {
            Employee employee = new Employee(data);
            EmployeeDao employeeSQLDao = daoFactory.getEmployeeDao();
            employeeSQLDao.insert(employee);
        } catch (Exception e){
            throw new RegisteringException("Erro ao inserir funcionário: " + e.getMessage(), e);
        }
    }

    public void deleteEmployee(Long userId, String auth) {
        try{
            String authorization = tokenSrv.getAuthorizationToken(auth);
            Long userIdToken = tokenSrv.getUserId(authorization);
            if(userIdToken.equals(userId)){
                throw new IllegalArgumentException("Não é possível excluir o próprio usuário");
            }
            Employee employee = new Employee(userId);
            EmployeeDao employeeSQLDao = daoFactory.getEmployeeDao();
            employeeSQLDao.delete(employee);
        } catch (IllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new RegisteringException("Erro ao excluir o empregado: " + e.getMessage(), e);
        }
    }

    public void updateEmployee(EmployeeInputDTO data, Long userId) {
        try{
            Employee employee = new Employee(data);
            employee.setId(userId);
            EmployeeDao employeeSQLDao = daoFactory.getEmployeeDao();
            employeeSQLDao.update(employee);
        } catch(Exception e){
            throw new RegisteringException("Erro ao atualizar o empregado: " + e.getMessage(), e);
        }
    }

    public List<EmployeeOutputDTO> listEmployees() {
        try{
            EmployeeDao employeeSQLDao = daoFactory.getEmployeeDao();
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
