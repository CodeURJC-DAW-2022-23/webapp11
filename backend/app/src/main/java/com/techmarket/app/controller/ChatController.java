package com.techmarket.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techmarket.app.Repositories.MessageRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Message;
import com.techmarket.app.model.User;
import com.techmarket.app.service.JSONService;
import com.techmarket.app.service.MessageService;
import com.techmarket.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String getMessages(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserName(auth.getName());
        // Get all messages associated with the current user
        List<Message> messages = messageService.getMessageById(currentUser.getId());
        // Show them by chronological order
        messages.sort(Comparator.comparing(Message::getTimestamp));
        model.addAttribute("messages", messages);
        model.addAttribute("agent", "");
        model.addAttribute("userId", "");
        return "messages";
    }

    @PostMapping("/send-message")
    public String sendMessage(Message message, @RequestParam String messageText) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserName(auth.getName());
        // Append "Name: " to the message
        messageText = currentUser.getFirstName() + ": " + messageText;
        message.setUser(currentUser);
        message.setMessage(messageText);
        messageService.saveMessage(message);
        return "redirect:/messages";
    }

    @PostMapping("/send-message/agent")
    public String sendMessageAgent(Message message, @RequestParam String messageText, @RequestParam("identification") Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserName(auth.getName());
        // Append "Agent: " to the message
        messageText = "Agent: " + messageText;
        message.setAgent(currentUser);
        // Get the user associated with the message
        User user = userService.getUserById(userId);
        message.setUser(user);
        message.setMessage(messageText);
        messageService.saveMessage(message);
        return "redirect:/messages/" + userId;
    }

    @GetMapping("/chats")
    public String getChats(Model model, @PageableDefault(size = 10) Pageable pageable) {
        // Get the user ids that are the role user
        List<User> userIds = userService.getUserByRole("USER");
        Page<Message> page = messageService.getAllMessages(pageable);
        List<User> messages = new ArrayList<>();
        // Get the messages associated with the user ids
        page.forEach(message -> {
            if (userIds.contains(message.getUser())) {
                // Check if the user is already in the list
                if (!messages.contains(message.getUser())) {
                    messages.add(message.getUser());
                }
            }
        });
        model.addAttribute("chats", messages);
        model.addAttribute("total", page.getTotalElements()); // Total number of results
        model.addAttribute("hasMore", page.hasNext()); // Boolean to check if there are more results
        return "chats";
    }

    @GetMapping("chats/loadmore")
    public ResponseEntity<String> loadMore(@RequestParam("start") int start) throws JsonProcessingException {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(start / pageSize, pageSize);
        // Get the user ids that are the role user
        List<User> userIds = userService.getUserByRole("USER");
        Page<Message> page = messageService.getAllMessages(pageable);
        List<User> messages = new ArrayList<>();
        // Get the messages associated with the user ids
        page.forEach(message -> {
            if (userIds.contains(message.getUser())) {
                // Check if the user is already in the list
                if (!messages.contains(message.getUser())) {
                    messages.add(message.getUser());
                }
            }
        });
        // Create a "page" of the results
        Page<User> userPage = new PageImpl<>(messages, pageable, page.getTotalElements());

        // Send the results to the ajax call
        return JSONService.getMessageStringResponseEntity(userPage);
    }

    @GetMapping("/messages/{id}")
    public String getMessages(Model model, @PathVariable("id") Long id) {
        // Get the messages associated with the chat
        List<Message> messages = messageService.getMessagesByUser(id);
        // Show them by chronological order
        messages.sort(Comparator.comparing(Message::getTimestamp));
        model.addAttribute("agent", "/agent");
        model.addAttribute("messages", messages);
        model.addAttribute("userId", id);
        return "messages";
    }
}