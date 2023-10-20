package com.example.demo.repositories;

import com.example.demo.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Integer>{

    @Query(
            value = "SELECT * FROM chat_message chm WHERE chm.chat_id = ?1",
            nativeQuery = true)
    List<ChatMessage> getChatHistory(int chat_id);

}
