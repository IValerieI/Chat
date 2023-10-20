package com.example.demo.services.impl;

import com.example.demo.dto.*;
import com.example.demo.exceptions.ChatNotFoundException;
import com.example.demo.models.Chat;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.Person;
import com.example.demo.repositories.ChatMessagesRepository;
import com.example.demo.repositories.ChatRepository;
import com.example.demo.services.IChatMessageService;
import com.example.demo.services.IPersonService;
import com.example.demo.utils.ChatName;
import com.example.demo.utils.PersonLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ChatMessageService implements IChatMessageService {

    private final ChatMessagesRepository chatMessagesRepository;
    private final ChatRepository chatRepository;
    private final PersonLogin personLogin;
    private final ChatName chatNameUtil;

    @Autowired
    public ChatMessageService(ChatRepository chatRepository, ChatMessagesRepository chatMessagesRepository,
                              PersonLogin personLogin, ChatName chatNameUtil) {
        this.chatMessagesRepository = chatMessagesRepository;
        this.chatRepository = chatRepository;
        this.personLogin = personLogin;
        this.chatNameUtil = chatNameUtil;
    }

    @Transactional
    @Override
    public ChatMessageDTO createChatMessage(ChatMessageDTO chatMessageDTO, String chat_name) {
        ChatMessage chatMessage = mapToEntity(chatMessageDTO);

        chatMessage.setSender(personLogin.getLogin());
        chatMessage.setChat(chatRepository.findByChatName(chat_name).orElseThrow(() -> new ChatNotFoundException("Чат не найден")));
        chatMessage.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        ChatMessage newChatMessage = chatMessagesRepository.save(chatMessage);
        return mapToDto(newChatMessage);
    }

    @Transactional
    @Override
    public ChatMessageDTO createChatMessageByLogin(ChatMessageDTO chatMessageDTO, String chat_with_login) {
        ChatMessage chatMessage = mapToEntity(chatMessageDTO);
        String chat_name = chatNameUtil.getChatName(chat_with_login);

        chatMessage.setSender(personLogin.getLogin());

        Chat chat = chatRepository.findByChatName(chat_name).orElseThrow(() -> new ChatNotFoundException("Чат не найден"));
        chatMessage.setChat(chat);

        chatMessage.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        ChatMessage newChatMessage = chatMessagesRepository.save(chatMessage);
        return mapToDto(newChatMessage);
    }

    public ChatMessageDTO getChatMessageById(int id) {
        ChatMessage chatMessage = chatMessagesRepository.findById(id).orElseThrow();
        return mapToDto(chatMessage);

    }

    @Override
    public List<ChatMessageDTO> getAllByChatName(String chat_name) {
        Chat chat = chatRepository.findByChatName(chat_name).orElseThrow(() -> new ChatNotFoundException("Чат не найден"));;
        int chat_id = chat.getId();
        List<ChatMessage> chatMessageList = chatMessagesRepository.getChatHistory(chat_id);
        List<ChatMessageDTO> chatMessageDTOList = chatMessageList.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return chatMessageDTOList;
    }

    @Override
    public List<ChatMessageDTO> getAllByChatWithLogin(String chat_with_login) {
        String chat_name = chatNameUtil.getChatName(chat_with_login);
        Chat chat = chatRepository.findByChatName(chat_name).orElseThrow(() -> new ChatNotFoundException("Чат не найден"));

        int chat_id = chat.getId();
        List<ChatMessage> chatMessageList = chatMessagesRepository.getChatHistory(chat_id);
        List<ChatMessageDTO> chatMessageDTOList = chatMessageList.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        return chatMessageDTOList;
    }

    private ChatMessageDTO mapToDto(ChatMessage chatMessage) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setText(chatMessage.getText());
        chatMessageDTO.setSender(chatMessage.getSender());
        chatMessageDTO.setCreatedAt(chatMessage.getCreatedAt());
        return chatMessageDTO;
    }

    private ChatMessage mapToEntity(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setText(chatMessageDTO.getText());
        return chatMessage;
    }


}
