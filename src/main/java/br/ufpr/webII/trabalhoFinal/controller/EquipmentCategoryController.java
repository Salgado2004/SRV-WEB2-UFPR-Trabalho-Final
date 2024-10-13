package br.ufpr.webII.trabalhoFinal.controller;
import br.ufpr.webII.trabalhoFinal.infra.service.EquipmentCategoryService;
import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.EquipmentCategoryListItemDTO;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/service/v1/equipment-category")
public class EquipmentCategoryController {
    @Autowired
    private EquipmentCategoryService equipmentCategoryService;

    @PostMapping("/new")
    public ResponseEntity<String> newEquipmentCategory(@RequestBody @Valid EquipmentCategoryInputDTO data){
        equipmentCategoryService.createEquipmentCategory(data);

        return ResponseEntity.status(HttpStatus.CREATED).body("Categoria de equipamento criada com sucesso!");
    }

    @GetMapping("/list")
    public ResponseEntity<List<EquipmentCategoryListItemDTO>> listEquipmentCategories(){
        List<EquipmentCategory> equipmentCategories = equipmentCategoryService.listEquipmentCategories();
        return ResponseEntity.ok(equipmentCategories.stream().map(EquipmentCategoryListItemDTO::new).toList());
    }

}
