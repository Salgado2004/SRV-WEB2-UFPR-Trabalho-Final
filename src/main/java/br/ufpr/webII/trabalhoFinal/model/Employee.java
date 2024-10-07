package br.ufpr.webII.trabalhoFinal.model;

import java.util.Date;

public class Employee extends User {
    private Date birthDate;

    public Employee() {
        super();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}