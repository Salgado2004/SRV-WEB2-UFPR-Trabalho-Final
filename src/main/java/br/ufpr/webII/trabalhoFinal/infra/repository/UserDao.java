package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.model.Customer;
import br.ufpr.webII.trabalhoFinal.domain.model.User;
import br.ufpr.webII.trabalhoFinal.domain.dto.CustomerOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class UserDao {
    ArrayList<User> users = new ArrayList<User>();

    public void save(User user) throws IOException {
        users.add(user);
        if(user.getClass().getSimpleName().equals("Customer")){
            JsonUtil.writeJsonToFile("static/clients.json", new CustomerOutputDTO((Customer) user));
        }
        System.out.println("Usuário salvo com sucesso!");
    }

    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
