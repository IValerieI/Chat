package com.example.demo.services;

import com.example.demo.dto.ChatDTO;

public interface IChatService {

    ChatDTO createChat(ChatDTO chatDTO);
    ChatDTO getChatByPerson(String chat_with_login);
    ChatDTO getChatByName(String chat_name);

}
