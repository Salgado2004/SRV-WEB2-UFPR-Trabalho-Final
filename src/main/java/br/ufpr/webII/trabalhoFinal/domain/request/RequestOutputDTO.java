package br.ufpr.webII.trabalhoFinal.domain.request;

public record RequestOutputDTO(
        Long requestId,
        Long equipmentCategoryId,
        String equipmentDesc,
        String defectDesc,
        double budget,
        String repairDesc,
        String customerOrientations,
        Long customerId) {
    public RequestOutputDTO(Request request) {
        this(request.getId(), request.getEquipmentCategory().getEquipCategoryId(), request.getEquipmentDesc(),
                request.getDefectDesc(), request.getBudget(), request.getRepairDesc(),
                request.getCustomerOrientations(), request.getCustomer().getId());
    }
}
