package br.ufpr.webII.trabalhoFinal.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record EquipmentCategoryInputDTO (
        Long equipCategoryId,
    @NotBlank 
        String categoryDesc
) {}
