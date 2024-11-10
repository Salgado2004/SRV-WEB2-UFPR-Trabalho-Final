package br.ufpr.webII.trabalhoFinal.infra.connection.sql;

import br.ufpr.webII.trabalhoFinal.domain.user.User;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.infra.connection.ConnectionFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSQLDao implements UserDao {

    private static UserSQLDao instance;
    private final ConnectionFactory connectionFactory;

    private UserSQLDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public static UserSQLDao getUserSQLDao(ConnectionFactory connectionFactory){
        if(instance == null){
            instance = new UserSQLDao(connectionFactory);
        }
        return instance;
    }

    @Override
    public void insert(User user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(User objeto) throws Exception {

    }

    @Override
    public void delete(User objeto) throws Exception {

    }

    @Override
    public List<User> listAll() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        try(Connection con = connectionFactory.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE profile = 'CUSTOMER'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = new Customer();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao listar usuários", e);
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }
}
