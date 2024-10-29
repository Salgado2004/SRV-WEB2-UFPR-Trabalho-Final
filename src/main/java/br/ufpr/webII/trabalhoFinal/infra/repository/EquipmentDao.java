package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

@Component
public class EquipmentDao {

    @Autowired
    JsonFileService jsonService;

    // Insert a new category
    public void insert(EquipmentCategory equipmentCategory) {
        try {
            // read json
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json",
                    new TypeReference<>() {
                    });

            // find max id
            Long id = 0L;
            for (EquipmentCategory category : categories) {
                if (category.getEquipCategoryId() > id) {
                    id = category.getEquipCategoryId();
                }
            }
            id++;
            equipmentCategory.setEquipCategoryId(id);

            // insert and save
            categories.add(equipmentCategory);
            jsonService.writeJsonToFile("equipmentCategory.json", categories);
        } catch (IOException e) {
            System.out.println("Erro ao inserir arquivos: " + e.getMessage());
        }
    }

    public List<EquipmentCategory> selectAll() {
        try {
            // read json
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json",
                new TypeReference<>() {
                });
            return categories;
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public EquipmentCategory select(Long id) {
        try {
            // read json
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json",
                    new TypeReference<>() {
                    });

            // find category by id
            for (EquipmentCategory category : categories) {
                if (category.getEquipCategoryId().equals(id)) {
                    return category;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivo: " + e.getMessage());
        }
        throw new ResourceNotFoundException("Categoria de equipamento não encontrada");
    }

    public void delete(Long id) {
        try {
            // read json
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json", new TypeReference<>() {});
            
            // find category by id and remove
            Iterator<EquipmentCategory> iterator = categories.iterator();
            while (iterator.hasNext()) {
                EquipmentCategory category = iterator.next();
                if (category.getEquipCategoryId().equals(id)) {
                    iterator.remove();
                    break;
                }
            }

            // save
            jsonService.writeJsonToFile("equipmentCategory.json", categories);
        } catch (IOException e) {
            System.out.println("Erro ao excluir arquivo: " + e.getMessage());
        }
    }

    public void update(EquipmentCategory equipmentCategory) {
        try {
            // read json
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json", new TypeReference<>() {});
            
            // find category by id
            Iterator<EquipmentCategory> iterator = categories.iterator();
            while(iterator.hasNext()) {
                EquipmentCategory category = iterator.next();
                if(category.getEquipCategoryId().equals(equipmentCategory.getEquipCategoryId())) {
                    // update
                    category.setCategoryDesc(equipmentCategory.getCategoryDesc());
                    break;
                }
            }

            // save
            jsonService.writeJsonToFile("equipmentCategory.json", categories);
        } catch (IOException e) {
            System.out.println("Erro ao alterar arquivo: " + e.getMessage());
        }
    }
}
