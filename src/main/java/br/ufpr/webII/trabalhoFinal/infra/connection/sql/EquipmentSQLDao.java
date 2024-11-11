/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection.sql;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.infra.connection.ConnectionFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.EquipmentDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateus
 */
public class EquipmentSQLDao implements EquipmentDao {

    private static EquipmentSQLDao instance;
    private ConnectionFactory connectionFactory;

    private EquipmentSQLDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public static EquipmentSQLDao getEquipmentSQLDao(ConnectionFactory connectionFactory){
        return instance == null ? instance = new EquipmentSQLDao(connectionFactory) : instance;
    }

    @Override
    public void insert(EquipmentCategory element) throws Exception {
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO public.equipment_category (name) VALUES (?)")){
            ps.setString(1, element.getCategoryDesc());
            ps.executeUpdate();
        } catch (Exception e){
            throw new Exception("Erro ao inserir categoria de equipamento", e);
        }
    }

    @Override
    public void update(EquipmentCategory objeto) throws Exception {
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE public.equipment_category SET name = ? WHERE id = ?")){
            ps.setString(1, objeto.getCategoryDesc());
            ps.setLong(2, objeto.getEquipCategoryId());
            ps.executeUpdate();
        } catch (Exception e){
            throw new Exception("Erro ao atualizar categoria de equipamento", e);
        }
    }

    @Override
    public void delete(EquipmentCategory objeto) throws Exception {
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE public.equipment_category SET active = false WHERE id = ?")){
            ps.setLong(1, objeto.getEquipCategoryId());
            ps.executeUpdate();
        } catch (Exception e){
            throw new Exception("Erro ao deletar categoria de equipamento", e);
        }
    }

    @Override
    public List<EquipmentCategory> listAll() throws Exception {
        ArrayList<EquipmentCategory> categories = new ArrayList<>();
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM public.equipment_category WHERE active = true")){
            while (ps.getResultSet().next()){
                EquipmentCategory category = new EquipmentCategory();
                category.setEquipCategoryId(ps.getResultSet().getLong("id"));
                category.setCategoryDesc(ps.getResultSet().getString("name"));
                categories.add(category);
            }
            return categories;
        } catch (Exception e){
            throw new Exception("Erro ao listar categorias de equipamento", e);
        }
    }

    @Override
    public EquipmentCategory select(EquipmentCategory objeto) throws Exception {
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM public.equipment_category WHERE id = ?")){
            ps.setLong(1, objeto.getEquipCategoryId());
            if (ps.getResultSet().next()){
                EquipmentCategory category = new EquipmentCategory();
                category.setEquipCategoryId(ps.getResultSet().getLong("id"));
                category.setCategoryDesc(ps.getResultSet().getString("name"));
                return category;
            }
            return null;
        } catch (Exception e){
            throw new Exception("Erro ao selecionar categoria de equipamento", e);
        }
    }
    
}
