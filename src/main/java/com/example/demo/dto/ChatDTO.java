package com.example.demo.dto;

import com.example.demo.models.Person;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class ChatDTO {

    @NotEmpty(message = "Поле не должно быть пустым")
    private String chat_name;

    @NotEmpty(message = "Поле не должно быть пустым")
    private String chat_with_name;

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public String getChat_with_name() {
        return chat_with_name;
    }

    public void setChat_with_name(String chat_with_name) {
        this.chat_with_name = chat_with_name;
    }

}
