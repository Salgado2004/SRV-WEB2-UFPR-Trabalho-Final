package br.ufpr.webII.trabalhoFinal.domain.equipment;

public record EquipmentCategoryOutputDTO (
    Long equipCategoryId,
    String categoryDesc
) {
    public EquipmentCategoryOutputDTO(EquipmentCategory equipmentCategory) {
        this(equipmentCategory.getEquipCategoryId(), equipmentCategory.getCategoryDesc());
    }

}
