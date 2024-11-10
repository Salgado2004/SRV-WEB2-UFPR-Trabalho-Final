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
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service/v1/email")
public class EmailController {
    
    @Autowired //precisa do autowired pq as classes não iniciaizam corretamente e parece q estã onulas
    private final EmailService service;

    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendEmail(@Valid @RequestBody MessageDTO messageDTO){
        service.sendEmail(messageDTO);
        return ResponseEntity.ok(messageDTO);
    }
}


