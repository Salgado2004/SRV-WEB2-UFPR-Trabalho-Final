package br.ufpr.webII.trabalhoFinal.controller;
import br.ufpr.webII.trabalhoFinal.infra.service.EquipmentCategoryService;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategoryInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategoryListItemDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategoryUpdateDTO;

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

    // create category
    @PostMapping("/new")
    public ResponseEntity<String> newEquipmentCategory(@RequestBody @Valid EquipmentCategoryInputDTO data){
        equipmentCategoryService.createEquipmentCategory(data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Categoria de equipamento criada com sucesso!");
    }

    // list all categories
    @GetMapping("/list")
    public ResponseEntity<List<EquipmentCategoryListItemDTO>> listEquipmentCategories(){
        List<EquipmentCategory> equipmentCategories = equipmentCategoryService.listEquipmentCategories();
        return ResponseEntity.ok(equipmentCategories.stream().map(EquipmentCategoryListItemDTO::new).toList());
    }

    // delete category by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEquipmentCategory(@PathVariable Long id){
        equipmentCategoryService.deleteEquipmentCategory(id);
        return ResponseEntity.noContent().build();
    }

    // update category by id
    @PutMapping("/update")
    public ResponseEntity<String> updateEquipmentCategory(@RequestBody @Valid EquipmentCategoryUpdateDTO data){
        equipmentCategoryService.updateEquipmentCategory(data);
        return ResponseEntity.status(HttpStatus.OK).body("Categoria de equipamento alterada com sucesso!");
    }

}
