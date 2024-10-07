package br.ufpr.webII.trabalhoFinal.repository;

import br.ufpr.webII.trabalhoFinal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    T findByEmail(String email);
}