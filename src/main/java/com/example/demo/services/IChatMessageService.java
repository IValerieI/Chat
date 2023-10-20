package com.example.demo.services;

import com.example.demo.dto.ChatMessageDTO;

import java.util.List;

public interface IChatMessageService {

    ChatMessageDTO createChatMessage(ChatMessageDTO chatMessageDTO, String chat_name);
    ChatMessageDTO createChatMessageByLogin(ChatMessageDTO chatMessageDTO, String chat_with_login);
    List<ChatMessageDTO> getAllByChatName(String chat_name);
    List<ChatMessageDTO> getAllByChatWithLogin(String chat_with_login);

}
