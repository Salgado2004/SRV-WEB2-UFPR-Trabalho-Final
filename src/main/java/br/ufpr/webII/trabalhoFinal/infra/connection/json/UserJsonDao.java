package br.ufpr.webII.trabalhoFinal.infra.connection.json;

import br.ufpr.webII.trabalhoFinal.domain.user.User;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.connection.UserDao;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserJsonDao implements UserDao {

    private static UserJsonDao instance;
    private final JsonFileService jsonService;

    private UserJsonDao(JsonFileService jsonFileService){
        this.jsonService = jsonFileService;
    }

    public static UserJsonDao getUserJsonDao(JsonFileService jsonFileService){
        if(instance == null){
            instance = new UserJsonDao(jsonFileService);
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
        return null;
    }

    @Override
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
