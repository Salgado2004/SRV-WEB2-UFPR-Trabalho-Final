package br.ufpr.webII.trabalhoFinal.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryOutputDTO;

import java.util.Objects;

@Entity
public class EquipmentCategory {

    private @Id @GeneratedValue Long equipCategoryId;
    private String categoryDesc;

    public EquipmentCategory() {}

    public EquipmentCategory(EquipmentCategoryInputDTO data) {
        this.categoryDesc = data.equipmentCategoryDesc();
    }

    public EquipmentCategory(EquipmentCategoryOutputDTO data) {
        this.equipCategoryId = data.equipmentCategoryId();
        this.categoryDesc = data.equipmentCategoryDesc();
    }

    public Long getId() {
        return equipCategoryId;
    }

    public void setId(Long id) {
        this.equipCategoryId = id;
    }

    public String getDescription() {
        return categoryDesc;
    }

    public void setDescription(String description) {
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
