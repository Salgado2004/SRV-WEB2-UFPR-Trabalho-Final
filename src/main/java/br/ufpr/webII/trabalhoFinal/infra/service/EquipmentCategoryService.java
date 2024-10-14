package br.ufpr.webII.trabalhoFinal.infra.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RequestException;
import br.ufpr.webII.trabalhoFinal.infra.repository.EquipmentDao;

@Service
public class EquipmentCategoryService {
    @Autowired
    private EquipmentDao equipmentDAO;

    public void createEquipmentCategory(EquipmentCategoryInputDTO data) {
        try {
            EquipmentCategory equipmentCategory = new EquipmentCategory(data);
            equipmentDAO.insert(equipmentCategory);
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        }
    }

    public List<EquipmentCategory> listEquipmentCategories() {
        try {
            return equipmentDAO.selectAll();    
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        }
    }

    public void deleteEquipmentCategory(Long id) {
        try {
            equipmentDAO.delete(id);
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        }
    }

    public void updateEquipmentCategory(EquipmentCategoryUpdateDTO data) {
        try {
            EquipmentCategory equipmentCategory = new EquipmentCategory(data);
            equipmentDAO.update(equipmentCategory);
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        }
    }
}
