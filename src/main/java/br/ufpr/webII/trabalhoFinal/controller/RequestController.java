package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.infra.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/v1/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/new")
    public ResponseEntity<String> newRequest(@RequestBody @Valid RequestInputDTO data) {
        requestService.createRequest(data);

        return ResponseEntity.status(HttpStatus.CREATED).body("Requisição de serviço criada com sucesso!");
    }

    @GetMapping("/list")
    public ResponseEntity<String> listRequests() {
        return ResponseEntity.ok("Listagem de requisições de serviço");
    }

    @GetMapping("/detail/:id")
    public ResponseEntity<String> detailRequest() {
        return ResponseEntity.ok("Detalhes da requisição de serviço");
    }
}
