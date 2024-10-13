package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;

public record EquipmentCategoryOutputDTO (
    Long equipmentCategoryId,
    String equipmentCategoryDesc
) {
    public EquipmentCategoryOutputDTO(EquipmentCategory equipmentCategory) {
        this(equipmentCategory.getId(), equipmentCategory.getDescription());
    }

}
