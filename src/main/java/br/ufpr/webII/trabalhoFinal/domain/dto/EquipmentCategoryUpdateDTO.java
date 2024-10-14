package br.ufpr.webII.trabalhoFinal.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipmentCategoryUpdateDTO(
    @NotNull
        Long equipCategoryId,
    @NotBlank
        String categoryDesc
) {}
