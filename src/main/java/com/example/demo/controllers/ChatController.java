package com.example.demo.controllers;

import com.example.demo.dto.ChatDTO;
import com.example.demo.models.Chat;
import com.example.demo.services.impl.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatDTO chatDTO) {
        return new ResponseEntity<>(chatService.createChat(chatDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> showById(@PathVariable("id") int id) {
        return ResponseEntity.ok(chatService.getChatById(id));
    }

    @GetMapping("/name/{chat_name}")
    public ResponseEntity<ChatDTO> showByName(@PathVariable("chat_name") String chat_name) {
        return ResponseEntity.ok(chatService.getChatByName(chat_name));
    }

    @GetMapping("/person/{chat_with_login}")
    public ResponseEntity<ChatDTO> showByPerson(@PathVariable("chat_with_login") String chat_with_login) {
        return ResponseEntity.ok(chatService.getChatByPerson(chat_with_login));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatDTO>> showAllChats() {
        return ResponseEntity.ok(chatService.findAll());
    }


}
