package br.ufpr.webII.trabalhoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import br.ufpr.webII.trabalhoFinal.infra.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufpr.webII.trabalhoFinal.domain.model.Customer;
import br.ufpr.webII.trabalhoFinal.domain.dto.CustomerInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.UserLoginDTO;

@RestController
@RequestMapping("/service/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerInputDTO customerInputDTO) {

        authService.isValidCpf(customerInputDTO.cpf());
        authService.isValidEmail(customerInputDTO.email());

        Customer customer = authService.registerCustomer(customerInputDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestParam String email, @RequestParam String password) {

        authService.isValidEmail(email);

        // Retorna status OK se o login for bem-sucedido
        UserLoginDTO userData = authService.login(email, password);

        return ResponseEntity.status(HttpStatus.OK).body(userData);
    }
}
