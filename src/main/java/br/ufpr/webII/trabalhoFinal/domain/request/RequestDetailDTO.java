package br.ufpr.webII.trabalhoFinal.domain.request;

import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerListDTO;

import java.util.List;

public record RequestDetailDTO(
        Long requestId,
        String equipmentDesc,
        EquipmentCategory equipCategory,
        String defectDesc,
        double budget,
        String rejectReason,
        String repairDesc,
        String customerOrientations,
        List<RequestStatusOutputDTO> status,
        CustomerListDTO customer
) {
    public RequestDetailDTO(Request request){
        this(
                request.getId(),
                request.getEquipmentDesc(),
                request.getEquipmentCategory(),
                request.getDefectDesc(),
                request.getBudget(),
                request.getRejectReason(),
                request.getRepairDesc(),
                request.getCustomerOrientations(),
                request.getRequestStatus().stream().map(e -> new RequestStatusOutputDTO(e, request.getId())).toList(),
                new CustomerListDTO(request.getCustomer())
        );
    }
}
