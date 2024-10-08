package br.ufpr.webII.trabalhoFinal.repository;

import br.ufpr.webII.trabalhoFinal.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDao {
    ArrayList<User> users = new ArrayList<User>();

    public void save(User user) {
        users.add(user);
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
