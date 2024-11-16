package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.infra.connection.json.EmployeeJsonDao;
import br.ufpr.webII.trabalhoFinal.infra.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/v1/employee")
public class EmployeeController {
    
    @Autowired
    private EmployeeJsonDao employeeCrud;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping()
    public ResponseEntity<String> newEmployee(@RequestBody @Valid Employee data) throws Exception {
        employeeCrud.insert(data);

        return ResponseEntity.status(HttpStatus.CREATED).body("Empregado criada com sucesso!");
    }
  
    @GetMapping("{id}")
    public ResponseEntity<String> detailEmployee(@PathVariable Long id) throws Exception{
        employeeCrud.listAll();
        
        return ResponseEntity.ok("Detalhes da requisição de Empregados");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws Exception{
        Long idLogged = tokenService.getUserId(auth);
        //employeeCrud.delete(emp);
        return ResponseEntity.ok().body("Empregado deletado com sucesso");
    }
    
    @PutMapping()
    public ResponseEntity<String> updateEmployee(@RequestBody @Valid Employee emp) throws Exception{
        employeeCrud.update(emp);
        return ResponseEntity.ok().body("Empregado atualizado com sucesso");
    }
}
