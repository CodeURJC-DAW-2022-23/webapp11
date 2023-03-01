package com.techmarket.app.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@EnableAutoConfiguration
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @ManyToOne
    private User user;

    @ManyToOne
    private User agent;

    private String message;

    private boolean status;  // True if the thread has been resolved, false otherwise

    public Message(Long messageId, User user, User agent, String message, boolean status) {
        this.messageId = messageId;
        this.user = user;
        this.agent = agent;
        this.message = message;
        this.status = status;
    }

    public Message() {

    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
