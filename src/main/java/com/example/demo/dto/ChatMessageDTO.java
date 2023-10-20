package com.example.demo.dto;

import com.example.demo.models.Chat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class ChatMessageDTO {

    @NotEmpty(message = "Поле не должно быть пустым")
    @Length(max = 2048, message = "Сообщение слишком длинное")
    private String text;

    @Length(max = 100)
    private String sender;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
