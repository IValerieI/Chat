package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatName {

    private final PersonLogin personLogin;

    @Autowired
    public ChatName(PersonLogin personLogin) {
        this.personLogin = personLogin;
    }

    public String getChatName(String chat_with_name) {
        System.out.println("                " + personLogin.getLogin() + "_" + chat_with_name);
        return personLogin.getLogin() + "_" + chat_with_name;
    }
}
