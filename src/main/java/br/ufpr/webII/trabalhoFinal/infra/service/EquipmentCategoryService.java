package br.ufpr.webII.trabalhoFinal.infra.service;

import java.util.List;

import br.ufpr.webII.trabalhoFinal.infra.connection.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategoryInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategoryUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RequestException;
import br.ufpr.webII.trabalhoFinal.infra.connection.EquipmentDao;

@Service
public class EquipmentCategoryService {
    @Autowired
    private DaoFactory daoFactory;
    public void createEquipmentCategory(EquipmentCategoryInputDTO data) {
        try {
            EquipmentCategory equipmentCategory = new EquipmentCategory(data);
            EquipmentDao equipmentDao = daoFactory.getEquipmentDao();
            equipmentDao.insert(equipmentCategory);
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        } catch (Exception e){
            throw new RequestException(e.getMessage());
        }
    }

    public List<EquipmentCategory> listEquipmentCategories() {
        try {
            EquipmentDao equipmentDao = daoFactory.getEquipmentDao();
            return equipmentDao.listAll();
        } catch (IllegalArgumentException e) {
            throw new RequestException(e.getMessage());
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
    }

    public void deleteEquipmentCategory(Long id) {
        try {
            EquipmentDao equipmentDao = daoFactory.getEquipmentDao();
            equipmentDao.delete(new EquipmentCategory(id));
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        } catch (Exception e){
            throw new RequestException(e.getMessage());
        }
    }

    public void updateEquipmentCategory(EquipmentCategoryUpdateDTO data) {
        try {
            EquipmentCategory equipmentCategory = new EquipmentCategory(data);
            EquipmentDao equipmentDao = daoFactory.getEquipmentDao();
            equipmentDao.update(equipmentCategory);
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        } catch (Exception e){
            throw new RequestException(e.getMessage());
        }
    }
}
