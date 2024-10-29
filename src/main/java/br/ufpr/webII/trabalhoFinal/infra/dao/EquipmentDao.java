/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.dao;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;

/**
 *
 * @author mateus
 */
public interface EquipmentDao extends DAO<EquipmentCategory> {
    
    public EquipmentCategory select(EquipmentCategory objeto) throws Exception;   
    
}
