package br.ufpr.webII.trabalhoFinal.domain.request;

import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;

import java.util.List;

public record RequestDetailDTO(
        Long requestId,
        String equipmentDesc,
        EquipmentCategory equipCategory,
        String defectDesc,
        double budget,
        String repairDesc,
        String customerOrientations,
        Long customerId,
        List<RequestStatusOutputDTO> status
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
                request.getRequestStatus().stream().map(e -> new RequestStatusOutputDTO(e, request.getId()) ).toList()
        );
    }
}
