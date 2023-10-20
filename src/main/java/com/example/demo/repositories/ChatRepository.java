package com.example.demo.repositories;

import com.example.demo.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer>{

    @Query(
            value = "SELECT * FROM Chat ch WHERE ch.chat_name = ?1",
            nativeQuery = true
    )
    Optional<Chat> findByChatName(String name);


}
