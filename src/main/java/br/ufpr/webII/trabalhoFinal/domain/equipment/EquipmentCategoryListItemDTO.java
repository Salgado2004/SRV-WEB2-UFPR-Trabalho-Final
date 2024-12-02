package br.ufpr.webII.trabalhoFinal.domain.equipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipmentCategoryListItemDTO (
    @NotNull
        Long equipCategoryId,
    @NotBlank
        String description
){
    public EquipmentCategoryListItemDTO (EquipmentCategory equipmentCategory){
        this(equipmentCategory.getEquipCategoryId(), equipmentCategory.getCategoryDesc());
    }
}
