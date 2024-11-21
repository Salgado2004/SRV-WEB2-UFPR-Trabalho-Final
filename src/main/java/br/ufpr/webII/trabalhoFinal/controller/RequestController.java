package br.ufpr.webII.trabalhoFinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.CommonResponse;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestDetailDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestListItemDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CategoryReport;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CommomReport;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CommomReportDTO;
import br.ufpr.webII.trabalhoFinal.infra.service.RequestService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/service/v1/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping()
    public ResponseEntity<CommonResponse> newRequest(@RequestBody @Valid RequestInputDTO data) {
        requestService.createRequest(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse("Requisição de serviço criada com sucesso!"));
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
    public ResponseEntity<CommonResponse> updateRequest(@RequestBody @Valid RequestUpdateDTO data) {
        requestService.updateRequest(data);
        return ResponseEntity.ok(new CommonResponse("Requisição de serviço atualizada com sucesso!"));
    }

    @PostMapping("/report")
    public ResponseEntity<List<CommomReport>> getCommomReport(@RequestBody @Valid  CommomReportDTO data){
        List<CommomReport> report = requestService.listCommomReport(data);
        return ResponseEntity.ok(report.stream().map(reportItem -> reportItem).toList());
    }

    @GetMapping("/report/category")
    public ResponseEntity<List<CategoryReport>> getCategoryReport(){
        List<CategoryReport> report = requestService.listCategoryReport();
        return ResponseEntity.ok(report.stream().map(reportItem -> reportItem).toList());
    }
    
    
}
