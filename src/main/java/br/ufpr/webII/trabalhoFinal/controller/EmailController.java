package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.email.MessageDTO;
import br.ufpr.webII.trabalhoFinal.infra.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailService service;

    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendEmail(@Valid @RequestBody MessageDTO messageDTO){
        service.sendEmail(messageDTO);
        return ResponseEntity.ok(messageDTO);
    }
}


