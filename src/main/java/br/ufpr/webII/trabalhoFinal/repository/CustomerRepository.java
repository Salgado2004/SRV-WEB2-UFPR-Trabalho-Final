package br.ufpr.webII.trabalhoFinal.repository;

import br.ufpr.webII.trabalhoFinal.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}