package com.techmarket.app.controller;

import com.techmarket.app.Repositories.MessageRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Message;
import com.techmarket.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/messages")
    public String getMessages(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        // Get all messages associated with the current user
        List<Message> messages = messageRepository.findByUserId(currentUser.getId());
        // Show them by chronological order
        messages.sort(Comparator.comparing(Message::getTimestamp));
        model.addAttribute("messages", messages);
        return "messages";
    }

    @PostMapping("/send-message")
    public String sendMessage(Message message, @RequestParam String messageText) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        message.setUser(currentUser);
        message.setMessage(messageText);
        messageRepository.save(message);
        return "redirect:/messages";
    }
}