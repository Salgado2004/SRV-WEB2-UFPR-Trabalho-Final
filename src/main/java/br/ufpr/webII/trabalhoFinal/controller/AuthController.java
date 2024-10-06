package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.dto.CustomerDTO;
import br.ufpr.webII.trabalhoFinal.model.Customer;
import br.ufpr.webII.trabalhoFinal.model.Employee;
import br.ufpr.webII.trabalhoFinal.service.AuthService;
import br.ufpr.webII.trabalhoFinal.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerDTO customerDTO) {
        // Valida CPF e E-mail no serviço
        if (!AuthService.isValidCpf(customerDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido.");
        }
        if (!AuthService.isValidEmail(customerDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail inválido.");
        }

        // Cria o cliente
        Customer customer = authService.registerCustomer(customerDTO);
        try {
            JsonUtil.writeJsonToFile("clients.json", customer);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar cliente.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @PostMapping("/customer/login/")
    public ResponseEntity<String> loginCustomer(@RequestParam String email, @RequestParam String password) {
        try {
            if (!AuthService.isValidEmail(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail inválido.");
            }

            Customer customer = (Customer) authService.login(email, password);
            if (customer != null) {
                // Retorna status OK se o login for bem-sucedido
                return ResponseEntity.status(HttpStatus.OK).body("Customer logou com sucesso");
            } else {
                // Retorna status UNAUTHORIZED se as credenciais forem inválidas
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais Inválidas.");
            }
        } catch (Exception e) {
            // Retorna status INTERNAL_SERVER_ERROR em caso de exceção
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar login.");
        }
    }
    @PostMapping("/employee/login/")
    public ResponseEntity<String> loginEmployee(@RequestParam String email, @RequestParam String password) {
        try {
            if (!AuthService.isValidEmail(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail inválido.");
            }

            // Tenta realizar o login do funcionário com o e-mail e senha fornecidos
            Employee employee = (Employee) authService.login(email, password);
            if (employee != null) {
                // Retorna status OK se o login for bem-sucedido
                return ResponseEntity.status(HttpStatus.OK).body("Employee logou com sucesso");
            } else {
                // Retorna status UNAUTHORIZED se as credenciais forem inválidas
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais Inválidas.");
            }
        } catch (Exception e) {
            // Retorna status INTERNAL_SERVER_ERROR em caso de exceção
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar login.");
        }
    }
}
