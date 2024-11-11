package br.ufpr.webII.trabalhoFinal.infra.connection.json;

import br.ufpr.webII.trabalhoFinal.domain.user.User;
import br.ufpr.webII.trabalhoFinal.infra.connection.UserDao;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;

import java.util.List;

public class UserJsonDao implements UserDao {

    private static UserJsonDao instance;
    private final JsonFileService jsonFileService;

    private UserJsonDao(JsonFileService jsonFileService){
        this.jsonFileService = jsonFileService;
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
        // TODO Auto-generated method stub
        return null;
    }
}
