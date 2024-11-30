/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection.sql;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CategoryReport;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CommomReport;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.infra.connection.ConnectionFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.DaoFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.RequestDao;

/**
 * @author mateus
 */
public class RequestSQLDao extends RequestDao {

    private static RequestSQLDao instance;
    private ConnectionFactory connectionFactory;

    private RequestSQLDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static RequestSQLDao getRequestSQLDao(ConnectionFactory connectionFactory) {
        return instance == null ? instance = new RequestSQLDao(connectionFactory) : instance;
    }

    public static RequestDao getRequestSQLDao() {
        return getRequestSQLDao(instance.connectionFactory);
    }

    @Override
    public void insert(Request request) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO public.request (equip_desc, defect_desc, budget, repair_desc, customer_orientations, equip_category_id, customer_id, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, request.getEquipmentDesc());
            ps.setString(2, request.getDefectDesc());
            ps.setDouble(3, request.getBudget());
            ps.setString(4, request.getRepairDesc());
            ps.setString(5, request.getCustomerOrientations());
            ps.setLong(6, request.getEquipmentCategory().getEquipCategoryId());
            ps.setLong(7, request.getCustomer().getId());
            ps.setBoolean(8, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                request.setId(rs.getLong(1));
            }
        } catch (Exception e) {
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
    public void requestUpdate(RequestUpdateDTO requestUpdateDTO) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement("")
        ) {


        } catch (Exception e) {
            throw new Exception("Erro ao atualizar requisição de serviço", e);
        }
    }

    @Override
    public void delete(Request request) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE public.request SET active = false WHERE id = ?")
        ) {
            ps.setLong(1, request.getId());
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
            EquipmentSQLDao equipmentSQLDao = new EquipmentSQLDao(connectionFactory);
            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getLong("id"));
                request.setEquipmentDesc(rs.getString("equip_desc"));
                request.setDefectDesc(rs.getString("defect_desc"));
                request.setBudget(rs.getDouble("budget"));
                request.setRepairDesc(rs.getString("repair_desc"));
                request.setCustomerOrientations(rs.getString("customer_orientations"));
                request.setEquipmentCategory(equipmentSQLDao.getById(rs.getLong("equip_category_id")));
                request.setRequestStatus(this.getStatusList(request.getId()));
                request.setCustomer(new Customer(rs.getLong("customer_id")));
                request.setActive(rs.getBoolean("active"));
                requests.add(request);
            }
            return requests;
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception("Erro ao listar requisições de serviço", e);
        }
    }

    @Override
    public Request getById(Long id) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM public.request WHERE id = ? AND active = true");
        ) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            EquipmentSQLDao equipmentSQLDao = new EquipmentSQLDao(connectionFactory);
            if (rs.next()) {
                Request request = new Request();
                request.setId(rs.getLong("id"));
                request.setEquipmentDesc(rs.getString("equip_desc"));
                request.setDefectDesc(rs.getString("defect_desc"));
                request.setBudget(rs.getDouble("budget"));
                request.setRepairDesc(rs.getString("repair_desc"));
                request.setCustomerOrientations(rs.getString("customer_orientations"));
                request.setEquipmentCategory(equipmentSQLDao.getById(rs.getLong("equip_category_id")));
                request.setRequestStatus(this.getStatusList(request.getId()));
                request.setCustomer(new Customer(rs.getLong("customer_id")));
                request.setActive(rs.getBoolean("active"));
                return request;
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar requisição de serviço", e);
        }
    }

    @Override
    public void insertStatus(RequestStatus requestStatus) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO public.request_status (date_time, request_id, sending_employee_id, in_charge_employee_id, status) VALUES (?, ?, ?, ?, ?)")) {
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(requestStatus.getDateTime()));
            ps.setLong(2, requestStatus.getRequest().getId());

            if (requestStatus.getSenderEmployee() != null && requestStatus.getSenderEmployee().getId() != null) {
                ps.setLong(3, requestStatus.getSenderEmployee().getId());
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);
            }

            if (requestStatus.getInChargeEmployee() != null && requestStatus.getInChargeEmployee().getId() != null){
                ps.setLong(4, requestStatus.getInChargeEmployee().getId());
            } else {
                ps.setNull(4, java.sql.Types.BIGINT);
            }

            ps.setObject(5, requestStatus.getCategory().toString().toUpperCase(), java.sql.Types.OTHER);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erro ao inserir status de requisição de serviço", e);
        }

    }

    public ArrayList<RequestStatus> getStatusList(Long requestId) throws Exception {
        ArrayList<RequestStatus> list = new ArrayList<>();
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM public.request_status WHERE request_id = ? ORDER BY id;")
        ) {
            ps.setLong(1, requestId);
            ResultSet rs = ps.executeQuery();
            EmployeeSQLDao employeeSQLDao = new EmployeeSQLDao(connectionFactory);
            while (rs.next()) {
                list.add(new RequestStatus(
                        rs.getLong("id"),
                        null,
                        RequestStatusCategory.fromString(rs.getString("status")),
                        employeeSQLDao.getById(rs.getLong("in_charge_employee_id")),
                        employeeSQLDao.getById(rs.getLong("sending_employee_id")),
                        new Timestamp(rs.getDate("date_time").getTime()).toLocalDateTime()
                ));
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    @Override
    public ArrayList<CommomReport> listCommomReport(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        ArrayList<CommomReport> reports = new ArrayList<>();
        String query = "SELECT DATE(rs.date_time) AS day, SUM(r.budget) AS total_revenue " +
                "FROM request_status rs " +
                "JOIN request r ON rs.request_id = r.id " +
                "WHERE rs.status = 'PAID' " +
                "AND (rs.date_time >= COALESCE(?, rs.date_time)) " +
                "AND (rs.date_time <= COALESCE(?, rs.date_time)) " +
                "GROUP BY DATE(rs.date_time) " +
                "ORDER BY DATE(rs.date_time);";
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setTimestamp(1, startDate != null ? Timestamp.valueOf(startDate) : null);
            ps.setTimestamp(2, endDate != null ? Timestamp.valueOf(endDate) : null);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("day");
                    LocalDateTime day = timestamp != null ? timestamp.toLocalDateTime() : null;
                    CommomReport report = new CommomReport(day, rs.getDouble("total_revenue"));
                    reports.add(report);
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao carregar dados do relatório");
        }
        return reports;
    }

    public ArrayList<CategoryReport> listCategoryReport() throws Exception {
        ArrayList<CategoryReport> reports = new ArrayList<>();
        String query = "SELECT " +
                "ec.category_desc AS category, " +
                "SUM(r.budget) AS total_revenue " +
                "FROM " +
                "request r " +
                "JOIN " +
                "equip_category ec ON r.equip_category_id = ec.id " +
                "JOIN " +
                "request_status rs ON rs.request_id = r.id " +
                "WHERE " +
                "rs.status = 'PAID' " +
                "GROUP BY " +
                "ec.category_desc " +
                "ORDER BY " +
                "ec.category_desc;";

        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryReport report = new CategoryReport(rs.getString("category"), rs.getDouble("total_revenue"));
                    reports.add(report);
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao carregar dados do relatório de categoria");
        }
        return reports;
    }

    @Override
    public List<Request> listByUserId(String search) throws Exception {
        List<Request> lista = new ArrayList<>();
        try(    Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(search);
                ){
            try(ResultSet rs = ps.executeQuery()){
                EquipmentSQLDao equipmentSQLDao = new EquipmentSQLDao(connectionFactory);
                while (rs.next()){
                    Request request = new Request();
                    request.setId(rs.getLong("id"));
                    request.setEquipmentDesc(rs.getString("equip_desc"));
                    request.setDefectDesc(rs.getString("defect_desc"));
                    request.setBudget(rs.getDouble("budget"));
                    request.setRepairDesc(rs.getString("repair_desc"));
                    request.setCustomerOrientations(rs.getString("customer_orientations"));
                    request.setEquipmentCategory(equipmentSQLDao.getById(rs.getLong("equip_category_id")));
                    request.setRequestStatus(this.getStatusList(request.getId()));
                    request.setCustomer(new Customer(rs.getLong("customer_id")));
                    request.setActive(rs.getBoolean("active"));
                    lista.add(request);
                }
            }
            
        } catch (Exception e){
            throw new Exception("Erro ao pesquisar por usuario.");
        }
        return lista;
    }


}
