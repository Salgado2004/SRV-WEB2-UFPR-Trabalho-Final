package br.ufpr.webII.trabalhoFinal.domain.model;

import java.util.Date;

public class Employee extends User {

    public static long whoIsLoggedIn() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
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