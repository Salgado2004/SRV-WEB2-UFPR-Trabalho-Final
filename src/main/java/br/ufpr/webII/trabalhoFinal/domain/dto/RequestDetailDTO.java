package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.model.Request;

import java.util.List;

public record RequestDetailDTO(
        Long id,
        String equipmentDesc,
        EquipmentCategory equipmentCategory,
        String defectDesc,
        double budget,
        String repairDesc,
        String customerOrientations,
        Long customerId,
        List<RequestStatusOutputDTO> requestStatus
) {
    public RequestDetailDTO(Request request){
        this(
                request.getId(),
                request.getEquipmentDesc(),
                request.getEquipmentCategory(),
                request.getDefectDesc(),
                request.getBudget(),
                request.getRepairDesc(),
                request.getCustomerOrientations(),
                request.getCustomer().getId(),
                request.getRequestStatus().stream().map(RequestStatusOutputDTO::new).toList()
        );
    }
}