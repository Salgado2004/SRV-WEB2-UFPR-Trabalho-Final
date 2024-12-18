/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.webII.trabalhoFinal.domain.address.Address;
import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CategoryReport;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CommomReport;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.infra.connection.ConnectionFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.RequestDao;

/**
 * @author mateus
 */
public class RequestSQLDao extends RequestDao {

    private static RequestSQLDao instance;
    private final ConnectionFactory connectionFactory;

    private RequestSQLDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static RequestSQLDao getRequestSQLDao(ConnectionFactory connectionFactory) {
        return instance == null ? instance = new RequestSQLDao(connectionFactory) : instance;
    }

    @Override
    public void insert(Request request) throws Exception {
        try (Connection con = connectionFactory.getConnection()) {
            con.setAutoCommit(false);

            try(PreparedStatement ps = con.prepareStatement("INSERT INTO public.request (equip_desc, defect_desc, budget, repair_desc, customer_orientations, equip_category_id, customer_id, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)){
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
            }

            RequestStatus requestStatus = request.getRequestStatus().get(0);

            try (PreparedStatement ps = con.prepareStatement("INSERT INTO public.request_status (date_time, request_id, sending_employee_id, in_charge_employee_id, status) VALUES (?, ?, ?, ?, ?)")) {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(requestStatus.getDateTime()));
                ps.setLong(2, requestStatus.getRequest().getId());

                if (requestStatus.getSenderEmployee() != null && requestStatus.getSenderEmployee().getId() != null) {
                    ps.setLong(3, requestStatus.getSenderEmployee().getId());
                } else {
                    ps.setNull(3, java.sql.Types.BIGINT);
                }

                if (requestStatus.getInChargeEmployee() != null && requestStatus.getInChargeEmployee().getId() != null) {
                    ps.setLong(4, requestStatus.getInChargeEmployee().getId());
                } else {
                    ps.setNull(4, java.sql.Types.BIGINT);
                }

                ps.setObject(5, requestStatus.getCategory().toString().toUpperCase(), java.sql.Types.OTHER);
                ps.executeUpdate();
            } catch (Exception e) {
                throw new Exception("Erro ao inserir status de requisição de serviço", e);
            }

            con.commit();

        } catch (Exception e) {
            throw new Exception("Erro ao inserir requisição de serviço", e);
        }
    }

    @Override
    public void update(Request element) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE public.request SET equip_desc = ?, defect_desc = ?, budget = ?, reject_reason = ?, repair_desc = ?, customer_orientations = ?, equip_category_id = ?, customer_id = ?, active = ? WHERE id = ?")
        ) {
            ps.setString(1, element.getEquipmentDesc());
            ps.setString(2, element.getDefectDesc());
            ps.setDouble(3, element.getBudget());
            ps.setString(4, element.getRejectReason());
            ps.setString(5, element.getRepairDesc());
            ps.setString(6, element.getCustomerOrientations());
            ps.setLong(7, element.getEquipmentCategory().getEquipCategoryId());
            ps.setLong(8, element.getCustomer().getId());
            ps.setBoolean(9, element.getActive());
            ps.setLong(10, element.getId());
            ps.executeUpdate();
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
        throw new UnsupportedOperationException("Use o método com filtro -> listAll(String query, Long id)");
    }

    @Override
    public List<Request> listAll(String query, Long id) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(query)
        ) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getLong("id"));
                request.setEquipmentDesc(rs.getString("equip_desc"));
                request.setDefectDesc(rs.getString("defect_desc"));
                request.setRequestStatus(this.getStatusList(request.getId()));
                request.setCustomer(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_surname"),
                        rs.getString("customer_email"),
                        rs.getString("customer_phone"),
                        rs.getString("customer_cpf"),
                        new Address(
                                rs.getString("cep"),
                                rs.getString("uf"),
                                rs.getString("city"),
                                rs.getString("district"),
                                rs.getString("street"),
                                Integer.parseInt(rs.getString("number"))
                        )
                ));
                request.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                requests.add(request);
            }
            return requests;
        } catch (Exception e) {
            throw new Exception("Erro ao listar requisições de serviço", e);
        }
    }

    @Override
    public Request getById(Long id) throws Exception {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM proc_detailRequest(?);")
        ) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Request request = null;
            ArrayList<RequestStatus> statusList = new ArrayList<>();
            EquipmentSQLDao equipmentSQLDao = new EquipmentSQLDao(connectionFactory);
            EmployeeSQLDao employeeSQLDao = new EmployeeSQLDao(connectionFactory);


            if (rs.next()) {
                request = new Request();
                request.setId(rs.getLong("id"));
                request.setEquipmentDesc(rs.getString("equip_desc"));
                request.setDefectDesc(rs.getString("defect_desc"));
                request.setBudget(rs.getDouble("budget"));
                request.setRepairDesc(rs.getString("repair_desc"));
                request.setCustomerOrientations(rs.getString("customer_orientations"));
                request.setRejectReason(rs.getString("reject_reason"));
                request.setEquipmentCategory(equipmentSQLDao.getById(rs.getLong("equip_category_id")));
                request.setCustomer(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("cpf"),
                        new Address(
                                rs.getString("cep"),
                                rs.getString("uf"),
                                rs.getString("city"),
                                rs.getString("district"),
                                rs.getString("street"),
                                Integer.parseInt(rs.getString("number"))
                        )
                ));
                request.setActive(rs.getBoolean("active"));
            }
            statusList.add(new RequestStatus(
                    rs.getLong("rs_id"),
                    request,
                    RequestStatusCategory.fromString(rs.getString("status")),
                    employeeSQLDao.getById(rs.getLong("in_charge_employee_id")),
                    employeeSQLDao.getById(rs.getLong("sending_employee_id")),
                    rs.getTimestamp("date_time").toLocalDateTime()
            ));

            while (rs.next()){
                statusList.add(new RequestStatus(
                        rs.getLong("rs_id"),
                        request,
                        RequestStatusCategory.fromString(rs.getString("status")),
                        employeeSQLDao.getById(rs.getLong("in_charge_employee_id")),
                        employeeSQLDao.getById(rs.getLong("sending_employee_id")),
                        rs.getTimestamp("date_time").toLocalDateTime()
                ));
            }
            if (request != null) {
                request.setRequestStatus(statusList);
            }

            return request;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

            if (requestStatus.getInChargeEmployee() != null && requestStatus.getInChargeEmployee().getId() != null) {
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
                        rs.getTimestamp("date_time").toLocalDateTime()
                ));
            }
            rs.close();
        } catch (Exception e) {
            throw new Exception("Erro ao buscar status de requisição de serviço", e);
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

            double totalRevenue = 0.0;
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("day");
                    LocalDateTime day = timestamp != null ? timestamp.toLocalDateTime() : null;
                    double revenue = rs.getDouble("total_revenue");
                    totalRevenue += revenue;
                    CommomReport report = new CommomReport(day, revenue);
                    reports.add(report);
                }
            }

            reports.add(new CommomReport(null, totalRevenue));
        } catch (Exception e) {
            throw new Exception("Erro ao carregar dados do relatório");
        }
        return reports;
    }

    public ArrayList<CategoryReport> listCategoryReport() throws Exception {
        double totalRevenue = 0.0;

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
                PreparedStatement ps = con.prepareStatement(query)
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryReport report = new CategoryReport(rs.getString("category"), rs.getDouble("total_revenue"));
                    double revenue = rs.getDouble("total_revenue");
                    totalRevenue += revenue;
                    reports.add(report);
                }
            }
            reports.add(new CategoryReport("TOTAL", totalRevenue));
        } catch (Exception e) {
            throw new Exception("Erro ao carregar dados do relatório de categoria");
        }
        return reports;
    }
}
