package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void register() {
        // TODO
    }

    @PostMapping("/login")
    public void login() {
        // TODO
    }

    @PostMapping("/logout")
    public void logout() {
        // TODO
    }
}
