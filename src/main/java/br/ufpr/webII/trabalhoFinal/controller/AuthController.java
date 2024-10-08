package br.ufpr.webII.trabalhoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import br.ufpr.webII.trabalhoFinal.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

import br.ufpr.webII.trabalhoFinal.model.Customer;
import br.ufpr.webII.trabalhoFinal.model.dto.CustomerInputDTO;
import br.ufpr.webII.trabalhoFinal.model.dto.UserLoginDTO;

@RestController
@RequestMapping("/service/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerInputDTO customerInputDTO) {
        System.out.println(authService);
        // Valida CPF e E-mail no serviço
        if (!authService.isValidCpf(customerInputDTO.cpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido.");
        }
        if (authService.isInvalidEmail(customerInputDTO.email())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail inválido.");
        }

        try {
            // Cria o cliente
            Customer customer = authService.registerCustomer(customerInputDTO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar cliente.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam String email, @RequestParam String password) {
        try {
            if (authService.isInvalidEmail(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail inválido.");
            }

            UserLoginDTO userData = authService.login(email, password);
            if (userData != null) {
                // Retorna status OK se o login for bem-sucedido
                return ResponseEntity.status(HttpStatus.OK).body(userData);
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
