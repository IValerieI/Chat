package com.example.demo.models;

import com.example.demo.token.Token;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 15, message = "Длина имени не больше 15 символов")
    private String name;

    @Column(name = "lastname")
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 15, message = "Длина фамилии не больше 15 символов")
    private String lastname;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "Поле не должно быть пустым")
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats = new ArrayList<Chat>();

    @OneToMany(mappedBy = "person")
    private List<Token> tokens;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Person_Role", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
