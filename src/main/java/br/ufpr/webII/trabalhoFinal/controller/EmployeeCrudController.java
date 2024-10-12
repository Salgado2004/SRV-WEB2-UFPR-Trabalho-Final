package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.dto.EmployeeInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.model.Employee;
import br.ufpr.webII.trabalhoFinal.infra.service.EmployeeCrud;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service/v1/employee")
public class EmployeeCrudController {
    
    @Autowired
    private EmployeeCrud employeeCrud;
    
    @PostMapping("/new")
    public ResponseEntity<String> newEmployee(@RequestBody @Valid Employee data) {
        employeeCrud.registerEmployee(data);

        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado criada com sucesso!");
    }
  
    @GetMapping("/detail/:id")
    public ResponseEntity<String> detailEmployee(){
        //implementar a listagem.
        return ResponseEntity.ok("Detalhes da requisição de Empregados");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteEmployee(Employee emp){
        employeeCrud.deleteEmployee(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado deletado com sucesso");// Não sei se é esse created ou não
    }
    
    @PostMapping("/update")
    public ResponseEntity<String> updateEmployee(Employee emp){
        employeeCrud.updateEmployee(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado atualizado com sucesso");//Não sei se é esse created ou nao
    }
}
