package com.example.demo.services.impl;

import com.example.demo.dto.ChatDTO;
import com.example.demo.dto.PersonDTO;
import com.example.demo.exceptions.ChatNotFoundException;
import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.models.Chat;
import com.example.demo.models.Person;
import com.example.demo.repositories.ChatRepository;
import com.example.demo.repositories.PeopleRepository;
import com.example.demo.services.IChatService;
import com.example.demo.utils.ChatName;
import com.example.demo.utils.PersonLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class ChatService implements IChatService {

    private final ChatRepository chatRepository;
    private final PeopleRepository peopleRepository;
    private final PersonLogin personLogin;
    private final ChatName chatNameUtil;

    @Autowired
    public ChatService(ChatRepository chatRepository, PeopleRepository peopleRepository,
                       PersonLogin personLogin, ChatName chatNameUtil) {
        this.chatRepository = chatRepository;
        this.peopleRepository = peopleRepository;
        this.personLogin = personLogin;
        this.chatNameUtil = chatNameUtil;
    }

    @Transactional
    @Override
    public ChatDTO createChat(ChatDTO chatDTO) {
        Chat chat = new Chat();

        String person_login = personLogin.getLogin();
        String chat_with_login = chatDTO.getChat_with_name();

        Person person = peopleRepository.findByLogin(person_login).orElseThrow(() -> new ChatNotFoundException("Пользователь не найден"));
        Person chat_with = peopleRepository.findByLogin(chat_with_login).orElseThrow(() -> new ChatNotFoundException("Пользователь не найден"));

        chat.setPerson(person);
        chat.setChat_with_id(chat_with.getId());

        String chat_name = chatNameUtil.getChatName(chat_with_login);
        chat.setChat_name(chat_name);

        Chat newChat = chatRepository.save(chat);

        return mapToDto(newChat);
    }

    @Override
    public ChatDTO getChatByPerson(String chat_with_login) {
        String chat_name = chatNameUtil.getChatName(chat_with_login);
        return getChatByName(chat_name);
    }

    public List<ChatDTO> findAll() {
        List<Chat> listOfChats = chatRepository.findAll();
        List<ChatDTO> listOfChatDTO = listOfChats.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return listOfChatDTO;
    }


    public ChatDTO getChatByPeople(String person_login, String chat_with_login) {
        String chat_name = chatNameUtil.getChatName(chat_with_login);
        return getChatByName(chat_name);
    }

    public ChatDTO getChatByName(String chat_name) {
        Chat chat = chatRepository.findByChatName(chat_name).orElseThrow(() -> new ChatNotFoundException("Чат не найден"));;
        return mapToDto(chat);
    }


    public ChatDTO getChatById(int id) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new ChatNotFoundException("Чат не найден"));
        return mapToDto(chat);
    }

    private ChatDTO mapToDto(Chat chat) {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setChat_name(chat.getChat_name());
        chatDTO.setChat_with_name(getChatWithLogin(chat.getChat_with_id()));
        return chatDTO;
    }

    private Chat mapToEntity(ChatDTO chatDTO) {
        Chat chat = new Chat();
        return chat;
    }

    private String getChatWithLogin(int id) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new ChatNotFoundException("Пользователь не найден"));
        String login = person.getLogin();
        return login;
    }


}
