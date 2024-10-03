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
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        // Calcula os dígitos verificadores
        int[] cpfDigits = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfDigits[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Cálculo do primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfDigits[i] * (10 - i);
        }
        int firstDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        // Cálculo do segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += cpfDigits[i] * (11 - i);
        }
        int secondDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        // Verifica se os dígitos verificadores estão corretos
        return cpfDigits[9] == firstDigit && cpfDigits[10] == secondDigit;
    }


    // Função para validação de e-mail
    private boolean isValidEmail(String email) {
        // Verifica se o e-mail não é nulo e se contém o símbolo "@" seguido de um domínio que termina com ".com"
        return email != null && email.contains("@") && email.matches("[^@\\s]+@[^@\\s]+\\.com");
    }

}
