package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.model.Customer;
import br.ufpr.webII.trabalhoFinal.domain.model.User;
import br.ufpr.webII.trabalhoFinal.domain.dto.CustomerOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {

    @Autowired
    JsonFileService jsonService;

    public void insert(User user) throws IOException {
        if (user.getClass().getSimpleName().equals("Customer")) {
            List<CustomerOutputDTO> data = jsonService.readObjectFromFile("clients.json", new TypeReference<>() {});
            data.add(new CustomerOutputDTO((Customer) user));
            jsonService.writeJsonToFile("clients.json", data);
        }
        System.out.println("Usuário salvo com sucesso!");
    }

    public User findByEmail(String email) {
        try {
            List<CustomerOutputDTO> customers = jsonService.readObjectFromFile("clients.json", new TypeReference<>(){});
            List<User> users = new ArrayList<>(customers.stream().map(Customer::new).toList());
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao consultar arquivos: "+ e.getMessage());
        }
        return null;
    }
}
