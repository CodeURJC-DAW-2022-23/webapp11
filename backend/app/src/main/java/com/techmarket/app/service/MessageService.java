package com.techmarket.app.service;


import com.techmarket.app.Repositories.MessageRepository;
import com.techmarket.app.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;


    public List<Message> getMessageById(Long id) {
        return messageRepository.findByUserId(id);
    }

    public Page<Message> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getMessagesByUser(Long id) {
        return messageRepository.findByUserId(id);
    }
}
