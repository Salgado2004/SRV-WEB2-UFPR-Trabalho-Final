package br.ufpr.webII.trabalhoFinal.repository;

import br.ufpr.webII.trabalhoFinal.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
}