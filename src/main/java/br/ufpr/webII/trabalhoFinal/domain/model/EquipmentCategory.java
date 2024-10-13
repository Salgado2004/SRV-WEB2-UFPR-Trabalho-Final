package br.ufpr.webII.trabalhoFinal.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryUpdateDTO;

import java.util.Objects;

@Entity
public class EquipmentCategory {

    private @Id @GeneratedValue Long equipCategoryId;
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
