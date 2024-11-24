package br.ufpr.webII.trabalhoFinal.infra.connection;

import br.ufpr.webII.trabalhoFinal.domain.user.User;

public interface UserDao extends DAO<User>{
    public User findByEmail(String email);
    public User getById(Long id);
}
