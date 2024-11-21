package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import br.ufpr.webII.trabalhoFinal.infra.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.UserLoginDTO;

@RestController
@RequestMapping("/service/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> register(@RequestBody @Valid CustomerInputDTO customerInputDTO) {

        authService.isValidCpf(customerInputDTO.cpf());
        authService.isValidEmail(customerInputDTO.email());

        Customer customer = authService.registerCustomer(customerInputDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse("Cliente cadastrado com sucesso!"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestParam String email, @RequestParam String password) {

        UserLoginDTO userData = authService.login(email, password);

        return ResponseEntity.ok().body(userData);
    }
}
