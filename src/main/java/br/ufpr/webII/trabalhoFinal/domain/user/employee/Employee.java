package br.ufpr.webII.trabalhoFinal.domain.user.employee;

import br.ufpr.webII.trabalhoFinal.domain.user.User;

import java.util.Date;

public class Employee extends User {

    private Long employeeId;
    private Date birthDate;

    public Employee() {
        super();
    }

    public Employee(EmployeeInputDTO emp) {
        super(emp.email(), emp.name(), emp.surname(), emp.password());
        this.birthDate = emp.birthDate();
    }

    public Employee(EmployeeOutputDTO emp) {
        throw new UnsupportedOperationException("No fields populated yet.");
    }

    public Employee(Long id) {
        this.employeeId = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}