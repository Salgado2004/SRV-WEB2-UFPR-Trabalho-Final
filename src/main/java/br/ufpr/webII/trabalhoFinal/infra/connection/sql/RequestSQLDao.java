/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection.sql;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.infra.connection.ConnectionFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.RequestDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateus
 */
public class RequestSQLDao implements RequestDao {

    private static RequestSQLDao instance;
    private ConnectionFactory connectionFactory;

    private RequestSQLDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public static RequestSQLDao getRequestSQLDao(ConnectionFactory connectionFactory){
        return instance == null ? instance = new RequestSQLDao(connectionFactory) : instance;
    }

    public static RequestDao getRequestSQLDao() {
        return getRequestSQLDao(instance.connectionFactory);
    }

    @Override
    public void insert(Request element) throws Exception {
        try(
        Connection con = connectionFactory.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO public.request (equip_desc, defect_desc, budget, repair_desc, customer_orientations, equip_category_id, customer_id, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")){
            ps.setString(1, element.getEquipmentDesc());
            ps.setString(2, element.getDefectDesc());
            ps.setDouble(3, element.getBudget());
            ps.setString(4, element.getRepairDesc());
            ps.setString(5, element.getCustomerOrientations());
            ps.setLong(6, element.getEquipmentCategory().getEquipCategoryId());
            ps.setLong(7, element.getCustomer().getId());
            ps.setBoolean(8, true);
            ps.executeUpdate();
        }catch (Exception e){
            throw new Exception("Erro ao inserir requisição de serviço", e);
        }
    }

    @Override
    public void update(Request element) throws Exception {
        try (
            Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE public.request SET equip_desc = ?, defect_desc = ?, budget = ?, repair_desc = ?, customer_orientations = ?, equip_category_id = ?, customer_id = ?, active = ? WHERE id = ?")
        ) {
            ps.setString(1, element.getEquipmentDesc());
            ps.setString(2, element.getDefectDesc());
            ps.setDouble(3, element.getBudget());
            ps.setString(4, element.getRepairDesc());
            ps.setString(5, element.getCustomerOrientations());
            ps.setLong(6, element.getEquipmentCategory().getEquipCategoryId());
            ps.setLong(7, element.getCustomer().getId());
            ps.setBoolean(8, element.getActive());
            ps.setLong(9, element.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar requisição de serviço", e);
        }
    }

    @Override
    public void delete(Request element) throws Exception {
        try (
            Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE public.request SET active = ? WHERE id = ?")
        ) {
            ps.setBoolean(1, false);
            ps.setLong(2, element.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar requisição de serviço", e);
        }    
    }

    @Override
    public List<Request> listAll() throws Exception {
        try (
            Connection con = connectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM public.request WHERE active = true")
        ) {
            ResultSet rs = ps.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getLong("id"));
                request.setEquipmentDesc(rs.getString("equip_desc"));
                request.setDefectDesc(rs.getString("defect_desc"));
                request.setBudget(rs.getDouble("budget"));
                request.setRepairDesc(rs.getString("repair_desc"));
                request.setCustomerOrientations(rs.getString("customer_orientations"));
                request.setEquipmentCategory(null);
                request.setCustomer(null);
                request.setActive(rs.getBoolean("active"));
                requests.add(request);
            }
            return requests;
        } catch (Exception e) {
            throw new Exception("Erro ao listar requisições de serviço", e);
        }
    }

    @Override
    public void insertStatus(RequestStatus requestStatus) throws Exception {
        try(
        Connection con = connectionFactory.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO public.request_status (id, date_time, request_id, sending_employee_id, in_charge_employee_id, status, active) VALUES (?, ?, ?, ?, ?, ?, ?)")){
            ps.setLong(1, requestStatus.getRequestStatusId());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(requestStatus.getDateTime()));
            ps.setLong(3, requestStatus.getRequest().getId());
            ps.setLong(4, requestStatus.getSenderEmployee().getId());
            ps.setLong(5, requestStatus.getInChargeEmployee().getId());
            ps.setString(6, requestStatus.getCategory().toString());
            ps.setBoolean(7, true);
            ps.executeUpdate();
           
        
        }catch (Exception e){
            throw new Exception("Erro ao inserir status de requisição de serviço", e);
        }
    
    }
}
