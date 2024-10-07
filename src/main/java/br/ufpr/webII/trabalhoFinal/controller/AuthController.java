package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.dto.CustomerDTO;
import br.ufpr.webII.trabalhoFinal.model.Customer;
import br.ufpr.webII.trabalhoFinal.service.AuthService;
import br.ufpr.webII.trabalhoFinal.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/service/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerDTO customerDTO) {
        System.out.println(authService);
        // Valida CPF e E-mail no serviço
        if (!authService.isValidCpf(customerDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido.");
        }
        if (!authService.isValidEmail(customerDTO.getEmail())) {
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
}
