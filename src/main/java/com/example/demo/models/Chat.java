package com.example.demo.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chat_name")
    private String name;

    @Column(name = "chat_with_id")
    private int chat_with_id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChat_name() {
        return name;
    }

    public void setChat_name(String chat_name) {
        this.name = chat_name;
    }

    public int getChat_with_id() {
        return chat_with_id;
    }

    public void setChat_with_id(int chat_with_id) {
        this.chat_with_id = chat_with_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
