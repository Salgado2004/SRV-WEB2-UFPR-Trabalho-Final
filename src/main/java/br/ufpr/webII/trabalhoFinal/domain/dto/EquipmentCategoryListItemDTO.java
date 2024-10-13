package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipmentCategoryListItemDTO (
    @NotNull
        Long equipmentCategoryId,
    @NotBlank
        String equipmentCategoryDesc
){
    public EquipmentCategoryListItemDTO (EquipmentCategory equipmentCategory){
        this(equipmentCategory.getId(), equipmentCategory.getDescription());
    }
}
