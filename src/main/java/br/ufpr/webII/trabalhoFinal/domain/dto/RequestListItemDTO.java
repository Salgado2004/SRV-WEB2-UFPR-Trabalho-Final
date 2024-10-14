package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.Request;

import java.time.LocalDateTime;

public record RequestListItemDTO(
        Long id,
        String title,
        String description,
        String status,
        String equipmentCategory,
        LocalDateTime created_at) {
    public RequestListItemDTO(Request request){
        this(
                request.getId(),
                request.getEquipmentDesc(),
                request.getDefectDesc(),
                request.getRequestStatus().get(request.getRequestStatus().size() - 1).getCategory().toString(),
                request.getEquipmentCategory().getCategoryDesc(),
                request.getRequestStatus().get(0).getDateTime()
        );
    }
}
