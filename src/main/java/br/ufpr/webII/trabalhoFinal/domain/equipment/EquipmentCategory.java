package br.ufpr.webII.trabalhoFinal.domain.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class EquipmentCategory {
    @JsonProperty("id")
    private Long equipCategoryId;
    @JsonProperty("description")
    private String categoryDesc;

    public EquipmentCategory() {}

    public EquipmentCategory(EquipmentCategoryInputDTO data) {
        this.equipCategoryId = 0L;
        this.categoryDesc = data.categoryDesc();
    }

    public EquipmentCategory(EquipmentCategoryOutputDTO data) {
        this.equipCategoryId = data.equipCategoryId();
        this.categoryDesc = data.categoryDesc();
    }

    public EquipmentCategory(EquipmentCategoryUpdateDTO data) {
        this.equipCategoryId = data.equipCategoryId();
        this.categoryDesc = data.categoryDesc();
    }

    public EquipmentCategory(Long id) {
        this.equipCategoryId = id;
    }

    public Long getEquipCategoryId() {
        return equipCategoryId;
    }

    public void setEquipCategoryId(Long id) {
        this.equipCategoryId = id;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String description) {
        this.categoryDesc = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentCategory that = (EquipmentCategory) o;
        return Objects.equals(equipCategoryId, that.equipCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipCategoryId);
    }
}
