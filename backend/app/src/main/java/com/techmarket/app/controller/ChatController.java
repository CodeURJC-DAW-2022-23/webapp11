package com.techmarket.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techmarket.app.Repositories.MessageRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Message;
import com.techmarket.app.model.User;
import com.techmarket.app.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        // Check if the messages are from a user or an agent
        messages.forEach(message -> {
            if (Objects.equals(message.getUser().getId(), currentUser.getId())) {
                model.addAttribute("isUser", true);
            } else {
                model.addAttribute("isUser", false);
            }
        });
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

    @GetMapping("/chats")
    public String getChats(Model model, @PageableDefault(size = 10) Pageable pageable) {
        // Get the page of chats
        Page<Message> page = messageRepository.findAll(pageable);
        model.addAttribute("chats", page.getContent());
        model.addAttribute("total", page.getTotalElements()); // Total number of results
        model.addAttribute("hasMore", page.hasNext()); // Boolean to check if there are more results
        return "chats";
    }

    @GetMapping("chats/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("start") int start) throws JsonProcessingException {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        Page<Message> page = messageRepository.findAll(pageable);

        // Send the results to the ajax call
        return JSONService.getMessageStringResponseEntity(page);
    }

    @GetMapping("/messages/{id}")
    public String getMessages(Model model, @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName());
        // Get all messages associated with the current user
        List<Message> messages = messageRepository.findByUserId(currentUser.getId());
        // Show them by chronological order
        messages.sort(Comparator.comparing(Message::getTimestamp));
        // Check if the messages are from a user or an agent
        messages.forEach(message -> {
            if (Objects.equals(message.getUser().getId(), currentUser.getId())) {
                model.addAttribute("isUser", false);
            } else {
                model.addAttribute("isUser", true);
            }
        });
        model.addAttribute("messages", messages);
        return "messages";
    }
}