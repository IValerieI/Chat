package com.example.demo.controllers;

import com.example.demo.dto.ChatMessageDTO;
import com.example.demo.services.impl.ChatMessageService;
import com.example.demo.services.impl.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat_message")
public class ChatMessagesController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessagesController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/{chat_name}")
    public ResponseEntity<ChatMessageDTO> createChatMessage(@RequestBody ChatMessageDTO chatMessageDTO, @PathVariable("chat_name") String chat_name) {
        return ResponseEntity.ok(chatMessageService.createChatMessage(chatMessageDTO, chat_name));
    }

    @PostMapping("/create/{chat_with_login}")
    public ResponseEntity<ChatMessageDTO> createChatMessageByLogin(@RequestBody ChatMessageDTO chatMessageDTO, @PathVariable("chat_with_login") String chat_with_login) {
        return ResponseEntity.ok(chatMessageService.createChatMessageByLogin(chatMessageDTO, chat_with_login));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatMessageDTO> showById(@PathVariable("id") int id) {
        return ResponseEntity.ok(chatMessageService.getChatMessageById(id));
    }

    @GetMapping("/all/{chat_name}")
    public ResponseEntity<List<ChatMessageDTO>> showByChatName(@PathVariable("chat_name") String chat_name) {
        List<ChatMessageDTO> chatMessages = chatMessageService.getAllByChatName(chat_name);
        return ResponseEntity.ok(chatMessages);
    }

    @GetMapping("/history/{chat_with_login}")
    public ResponseEntity<List<ChatMessageDTO>> showByLogin(@PathVariable("chat_with_login") String chat_with_login) {
        List<ChatMessageDTO> chatMessages = chatMessageService.getAllByChatWithLogin(chat_with_login);

        return ResponseEntity.ok(chatMessages);
    }



}
