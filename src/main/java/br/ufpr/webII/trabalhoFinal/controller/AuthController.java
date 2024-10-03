package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.dto.CustomerDTO;
import br.ufpr.webII.trabalhoFinal.model.Customer;
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
        // Valida CPF
        if (!isValidCpf(customerDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido.");
        }
        // Valida E-mail
        if (!isValidEmail(customerDTO.getEmail())) {
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

    // Função para validação de CPF
    private boolean isValidCpf(String cpf) {
        // Verifica se o CPF tem 11 dígitos e é composto apenas por números
        return cpf != null && cpf.matches("\\d{11}");
    }

    // Função para validação de e-mail
    private boolean isValidEmail(String email) {
        // Verifica se o e-mail contém o domínio ".com"
        return email != null && email.matches("[^@\\s]+@[^@\\s]+\\.com");
    }
}
