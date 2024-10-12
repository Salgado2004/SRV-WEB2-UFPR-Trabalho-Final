package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestListItemDTO;
import br.ufpr.webII.trabalhoFinal.domain.model.Request;
import br.ufpr.webII.trabalhoFinal.infra.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<RequestListItemDTO>> listRequests() {
        List<Request> requests = requestService.listRequests();
        return ResponseEntity.ok(requests.stream().map(RequestListItemDTO::new).toList());
    }

    @GetMapping("/detail/:id")
    public ResponseEntity<String> detailRequest() {
        return ResponseEntity.ok("Detalhes da requisição de serviço");
    }
}
