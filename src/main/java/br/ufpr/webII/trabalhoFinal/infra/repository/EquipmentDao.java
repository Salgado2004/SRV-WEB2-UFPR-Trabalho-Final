package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryOutputDTO;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EquipmentDao {

    @Autowired
    JsonFileService jsonService;

    public void insert(EquipmentCategory equipmentCategory) {
        try {
            List<EquipmentCategoryOutputDTO> data = jsonService.readObjectFromFile("equipmentCategory.json", new TypeReference<>() {});
            long id = (long) (data.size() +1);
            equipmentCategory.setId(id);
            data.add(new EquipmentCategoryOutputDTO(equipmentCategory));
            jsonService.writeJsonToFile("equipmentCategory.json", data);
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: "+ e.getMessage());
        }
    }

    public List<EquipmentCategory> selectAll() {
        try {
            return jsonService.readObjectFromFile("equipmentCategory.json", new TypeReference<>(){});
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: " + e.getMessage());
            return null;
        }
    }

    public EquipmentCategory select(Long id) {
        try {
            List<EquipmentCategory> categories = jsonService.readObjectFromFile("equipmentCategory.json", new TypeReference<>(){});
            for (EquipmentCategory category : categories) {
                if (category.getId().equals(id)) {
                    return category;
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao consultar arquivos: "+ e.getMessage());
        }
        throw new ResourceNotFoundException("Categoria de equipamento não encontrada");
    }
}
