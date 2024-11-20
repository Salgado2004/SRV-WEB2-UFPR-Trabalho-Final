package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.EmployeeOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.service.EmployeeCrud;
import br.ufpr.webII.trabalhoFinal.infra.service.TokenService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/v1/employee")
public class EmployeeController {
    
    @Autowired
    private EmployeeCrud employeeCrud;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping()
    public ResponseEntity<String> newEmployee(@RequestBody @Valid EmployeeInputDTO data) throws Exception {
        employeeCrud.registerEmployee(data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado criado com sucesso!");
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeOutputDTO>> listEmployees() throws Exception {
        List<EmployeeOutputDTO> employees = employeeCrud.listEmployees();
        return ResponseEntity.ok().body(employees);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteEmployee(@RequestBody @Valid EmployeeInputDTO data, @PathVariable Long userId) throws Exception{
        employeeCrud.deleteEmployee(data, userId);
        return ResponseEntity.ok().body("Empregado excluído com sucesso");
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateEmployee(@RequestBody @Valid EmployeeInputDTO data, @PathVariable Long userId) throws Exception{
        employeeCrud.updateEmployee(data, userId);
        return ResponseEntity.ok().body("Empregado alterado com sucesso");
    }
}
