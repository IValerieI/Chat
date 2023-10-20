package com.example.demo.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {

    @NotEmpty(message = "Поле не должно быть пустым")
    private String login;

    @NotEmpty(message = "Поле не должно быть пустым")
    private String password;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 15, message = "Длина имени не больше 15 символов")
    private String name;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 15, message = "Длина фамилии не больше 15 символов")
    private String lastname;

    @Email
    @NotEmpty(message = "Поле не должно быть пустым")
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
