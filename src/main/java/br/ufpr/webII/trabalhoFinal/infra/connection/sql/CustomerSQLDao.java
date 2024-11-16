package br.ufpr.webII.trabalhoFinal.infra.connection.sql;

import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.infra.connection.CustomerDao;
import br.ufpr.webII.trabalhoFinal.infra.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateus
 */
public class CustomerSQLDao implements CustomerDao {

    private static CustomerSQLDao instance;
    private final ConnectionFactory connectionFactory;

    private CustomerSQLDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static CustomerDao getCustomerSQLDao(ConnectionFactory connectionFactory) {
        if (instance == null) {
            instance = new CustomerSQLDao(connectionFactory);
        }
        return instance;
    }

    @Override
    public void insert(Customer customer) throws Exception {
        try (Connection con = connectionFactory.getConnection()) {
            con.setAutoCommit(false);
            String sql = "INSERT INTO public.\"user\" (name, surname, email, password, profile) VALUES (?, ?, ?, ?, 'CUSTOMER')";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getPassword()+":"+customer.getSalt());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    customer.setId(rs.getLong(1));
                } else {
                    throw new SQLException("Erro ao salvar cliente no banco de dados: não foi possível recuperar o ID gerado.");
                }
            }
            sql = "INSERT INTO public.address (cep, uf, city, district, street, number) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, customer.getAddress().getCep());
                ps.setString(2, customer.getAddress().getUf());
                ps.setString(3, customer.getAddress().getCity());
                ps.setString(4, customer.getAddress().getDistrict());
                ps.setString(5, customer.getAddress().getStreet());
                ps.setInt(6, customer.getAddress().getNumber());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    customer.getAddress().setId(rs.getLong(1));
                } else {
                    throw new SQLException("Erro ao salvar cliente no banco de dados: não foi possível recuperar o ID do endereço gerado.");
                }
            }
            sql = "INSERT INTO public.customer (user_id, address_id, cpf, phone_number) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, customer.getId());
                ps.setLong(2, customer.getAddress().getId());
                ps.setString(3, customer.getCpf());
                ps.setString(4, customer.getPhone());
                ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            throw new Exception("Erro ao salvar cliente no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Customer customer) throws Exception {
        throw new UnsupportedOperationException("Ainda não suportado");
    }

    @Override
    public void delete(Customer customer) throws Exception {
        throw new UnsupportedOperationException("Ainda não suportado");
    }

    @Override
    public List<Customer> listAll() throws Exception {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT u.\"name\", u.surname, u.email, c.* FROM public.\"user\" u join customer c ON u.id = c.user_id WHERE u.profile = 'CUSTOMER' AND u.active = true;";
        try (Connection con = connectionFactory.getConnection(); PreparedStatement sql = con.prepareStatement(query)){
            ResultSet rs = sql.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getLong("id"));
                customer.setId(rs.getLong("user_id"));
                customer.setName(rs.getString("name"));
                customer.setSurname(rs.getString("surname"));
                customer.setEmail(rs.getString("email"));
                customer.setCpf(rs.getString("cpf"));
                customer.setPhone(rs.getString("phone_number"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao listar clientes", e);
        }
        return customers;
    }
    
}
