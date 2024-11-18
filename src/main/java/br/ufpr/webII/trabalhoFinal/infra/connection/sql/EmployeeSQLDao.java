package br.ufpr.webII.trabalhoFinal.infra.connection.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.infra.connection.ConnectionFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.EmployeeDao;

public class EmployeeSQLDao implements EmployeeDao {

    private static EmployeeSQLDao instance;
    private final ConnectionFactory connectionFactory;

    private EmployeeSQLDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static EmployeeDao getEmployeeSQLDao(ConnectionFactory connectionFactory) {
        if (instance == null) {
            instance = new EmployeeSQLDao(connectionFactory);
        }
        return instance;
    }

    @Override
    public void insert(Employee employee) throws Exception {
        try (Connection con = connectionFactory.getConnection()) {
            con.setAutoCommit(false);
            String sql = "INSERT INTO public.\"user\" (name, surname, email, password, profile) VALUES (?, ?, ?, ?, 'EMPLOYEE')";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, employee.getName());
                ps.setString(2, employee.getSurname());
                ps.setString(3, employee.getEmail());
                ps.setString(4, employee.getPassword()+":"+employee.getSalt());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    employee.setId(rs.getLong(1));
                } else {
                    throw new SQLException("Erro ao salvar funcionário no banco de dados: não foi possível recuperar o ID gerado.");
                }
            }
            sql = "INSERT INTO public.employee (user_id, birth_date) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, employee.getId());
                ps.setDate(2, new java.sql.Date(employee.getBirthDate().getTime()));
                ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            throw new Exception("Erro ao salvar funcionário no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Employee employee) throws Exception {
        throw new UnsupportedOperationException("Ainda não suportado");
    }

    @Override
    public void delete(Employee employee) throws Exception {
        throw new UnsupportedOperationException("Ainda não suportado");
    }

    @Override
    public List<Employee> listAll() throws Exception {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT u.\"name\", u.surname, u.email, e.* FROM public.\"user\" u join employee e ON u.id = e.user_id WHERE u.profile = 'EMPLOYEE' AND u.active = true;";
        try (Connection con = connectionFactory.getConnection(); PreparedStatement sql = con.prepareStatement(query)){
            ResultSet rs = sql.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getLong("id"));
                employee.setId(rs.getLong("user_id"));
                employee.setName(rs.getString("name"));
                employee.setSurname(rs.getString("surname"));
                employee.setEmail(rs.getString("email"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao listar funcionários", e);
        }
        return employees;
    }
    
}
