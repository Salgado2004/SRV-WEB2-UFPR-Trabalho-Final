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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateus
 */
public class EquipmentSQLDao implements EquipmentDao {

    private static EquipmentSQLDao instance;
    private final ConnectionFactory connectionFactory;

    public EquipmentSQLDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public static EquipmentSQLDao getEquipmentSQLDao(ConnectionFactory connectionFactory){
        return instance == null ? instance = new EquipmentSQLDao(connectionFactory) : instance;
    }

    @Override
    public void insert(EquipmentCategory element) throws Exception {
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO public.equip_category (category_desc) VALUES (?)")){
            ps.setString(1, element.getCategoryDesc());
            ps.executeUpdate();
        } catch (Exception e){
            throw new Exception("Erro ao inserir categoria de equipamento", e);
        }
    }

    @Override
    public void update(EquipmentCategory objeto) throws Exception {
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE public.equip_category SET category_desc = ? WHERE id = ?")){
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
            PreparedStatement ps = con.prepareStatement("UPDATE public.equip_category SET active = false WHERE id = ?")){
            ps.setLong(1, objeto.getEquipCategoryId());
            ps.executeUpdate();
        } catch (Exception e){
            throw new Exception("Erro ao deletar categoria de equipamento", e);
        }
    }

    @Override
    public List<EquipmentCategory> listAll() throws Exception {
        ArrayList<EquipmentCategory> categories = new ArrayList<>();
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM public.equip_category WHERE active = true");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EquipmentCategory category = new EquipmentCategory();
                category.setEquipCategoryId(rs.getLong("id"));
                category.setCategoryDesc(rs.getString("category_desc"));
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            throw new Exception("Erro ao listar categorias de equipamento", e);
        }
    }

    @Override
    public EquipmentCategory getById(Long id) throws Exception {
        try(Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM public.equip_category WHERE id = ? AND active = true")){
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EquipmentCategory category = new EquipmentCategory();
                    category.setEquipCategoryId(rs.getLong("id"));
                    category.setCategoryDesc(rs.getString("category_desc"));
                    return category;
                }
            }
            return null;
        } catch (Exception e){
            throw new Exception("Erro ao selecionar categoria de equipamento", e);
        }
    }

}
