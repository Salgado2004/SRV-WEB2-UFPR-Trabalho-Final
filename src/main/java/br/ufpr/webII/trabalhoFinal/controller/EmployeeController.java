package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.CommonResponse;
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
    public ResponseEntity<CommonResponse> newEmployee(@RequestBody @Valid EmployeeInputDTO data) throws Exception {
        employeeCrud.registerEmployee(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse("Empregado criado com sucesso!"));
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeOutputDTO>> listEmployees() throws Exception {
        List<EmployeeOutputDTO> employees = employeeCrud.listEmployees();
        return ResponseEntity.ok().body(employees);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse> deleteEmployee(@PathVariable Long userId, @RequestHeader("Authorization") String auth) throws Exception{
        employeeCrud.deleteEmployee(userId, auth);
        return ResponseEntity.ok().body(new CommonResponse("Empregado excluído com sucesso"));
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponse> updateEmployee(@RequestBody @Valid EmployeeInputDTO data, @PathVariable Long userId) throws Exception{
        employeeCrud.updateEmployee(data, userId);
        return ResponseEntity.ok().body(new CommonResponse("Empregado alterado com sucesso"));
    }
}
