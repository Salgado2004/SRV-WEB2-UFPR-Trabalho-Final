package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.infra.connection.json.EmployeeJsonDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/v1/employee")
public class EmployeeCrudController {
    
    @Autowired
    private EmployeeJsonDao employeeCrud;
    
    @PostMapping()
    public ResponseEntity<String> newEmployee(@RequestBody @Valid Employee data) throws Exception {
        employeeCrud.insert(data);

        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado criada com sucesso!");
    }
  
    @GetMapping("/detail/:id")
    public ResponseEntity<String> detailEmployee() throws Exception{
        employeeCrud.listAll();
        
        return ResponseEntity.ok("Detalhes da requisição de Empregados");
    }

    @DeleteMapping("/delete/:id")
    public ResponseEntity<String> deleteEmployee(Employee emp) throws Exception{
        employeeCrud.delete(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado deletado com sucesso");
    }
    
    @PutMapping("/update/:id")
    public ResponseEntity<String> updateEmployee(Employee emp) throws Exception{
        employeeCrud.update(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado atualizado com sucesso");
    }
}
