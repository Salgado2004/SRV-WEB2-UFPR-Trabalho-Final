package br.ufpr.webII.trabalhoFinal.domain.user;

import br.ufpr.webII.trabalhoFinal.infra.service.PasswordService;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public abstract class User {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private String salt;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public final void encryptPassword(String plainPassword) {
        this.salt = PasswordService.generateSalt();
        try {
            this.password = PasswordService.hashPassword(plainPassword, this.salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public final boolean checkPassword(String plainPassword) {
        try {
            return this.password.equals(PasswordService.hashPassword(plainPassword, this.salt));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}