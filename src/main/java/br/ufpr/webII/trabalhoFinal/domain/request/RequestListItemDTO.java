package br.ufpr.webII.trabalhoFinal.domain.request;


import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerListDTO;

import java.time.LocalDateTime;

public record RequestListItemDTO(
        Long id,
        String title,
        String description,
        String status,
        LocalDateTime created_at,
        CustomerListDTO client) {
    public RequestListItemDTO(Request request) {
        this(
                request.getId(),
                request.getEquipmentDesc(),
                request.getDefectDesc(),
                request.getRequestStatus().get(request.getRequestStatus().size() - 1).getCategory().toString(),
                request.getRequestStatus().get(request.getRequestStatus().size() - 1).getDateTime(),
                new CustomerListDTO(request.getCustomer())
        );
    }
}