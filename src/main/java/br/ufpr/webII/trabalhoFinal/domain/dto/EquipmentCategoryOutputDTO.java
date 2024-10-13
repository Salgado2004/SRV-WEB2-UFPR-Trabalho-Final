package br.ufpr.webII.trabalhoFinal.domain.dto;

import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;

public record EquipmentCategoryOutputDTO (
    Long equipCategoryId,
    String categoryDesc
) {
    public EquipmentCategoryOutputDTO(EquipmentCategory equipmentCategory) {
        this(equipmentCategory.getEquipCategoryId(), equipmentCategory.getCategoryDesc());
    }

}
