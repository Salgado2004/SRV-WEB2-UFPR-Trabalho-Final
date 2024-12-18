/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection.json;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.infra.connection.EquipmentDao;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.connection.JsonFileWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mateus
 */
public class EquipmentJsonDao implements EquipmentDao {
    
    private static EquipmentJsonDao equipmentDao;
    private final JsonFileWriter jsonService;

    EquipmentJsonDao(JsonFileWriter jsonFileWriter){
        this.jsonService = jsonFileWriter;
    }

    public static EquipmentDao getEquipmentJsonDao(JsonFileWriter jsonService){
        if (equipmentDao == null)
            equipmentDao = new EquipmentJsonDao(jsonService);
        return equipmentDao;
    }

    @Override
    public void insert(EquipmentCategory equipmentCategory) throws Exception {
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

    @Override
    public void update(EquipmentCategory equipmentCategory) throws Exception {
        try {
            // read json
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json", new TypeReference<>() {});
            
            // find category by id
            for (EquipmentCategory category : categories) {
                if (category.getEquipCategoryId().equals(equipmentCategory.getEquipCategoryId())) {
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

    @Override
    public void delete(EquipmentCategory objeto) throws Exception {
        try {
            // read json
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json", new TypeReference<>() {});
            
            // find category by id and remove
            Iterator<EquipmentCategory> iterator = categories.iterator();
            while (iterator.hasNext()) {
                EquipmentCategory category = iterator.next();
                if (category.getEquipCategoryId().equals(objeto.getEquipCategoryId())) {
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

    @Override
    public List<EquipmentCategory> listAll() throws Exception {
        try {
            // read json
            return jsonService.readObjectFromFile("equipmentCategory.json",
                new TypeReference<>() {
                });
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
    @Override
    public EquipmentCategory getById(Long id) {
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
    
}
