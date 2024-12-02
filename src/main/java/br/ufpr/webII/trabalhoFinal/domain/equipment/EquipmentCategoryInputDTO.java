package br.ufpr.webII.trabalhoFinal.domain.equipment;

import jakarta.validation.constraints.NotBlank;

public record EquipmentCategoryInputDTO (
        Long equipCategoryId,
    @NotBlank 
        String description
) {}
