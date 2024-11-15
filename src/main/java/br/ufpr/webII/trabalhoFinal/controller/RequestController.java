package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.domain.request.RequestDetailDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestListItemDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.infra.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;

import java.util.List;

@RestController
@RequestMapping("/service/v1/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping()
    public ResponseEntity<String> newRequest(@RequestBody @Valid RequestInputDTO data) {
        requestService.createRequest(data);

        return ResponseEntity.status(HttpStatus.CREATED).body("Requisição de serviço criada com sucesso!");
    }

    @GetMapping()
    public ResponseEntity<List<RequestListItemDTO>> listRequests() {
        List<Request> requests = requestService.listRequests();
        return ResponseEntity.ok(requests.stream().map(RequestListItemDTO::new).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<RequestDetailDTO> detailRequest(@PathVariable Long id) {
        Request request = requestService.detailRequest(id);
        return ResponseEntity.ok(new RequestDetailDTO(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRequest(@RequestBody @Valid RequestUpdateDTO data) {
        requestService.updateRequest(data);
        return ResponseEntity.ok("Requisição de serviço atualizada com sucesso!");
    }
}
