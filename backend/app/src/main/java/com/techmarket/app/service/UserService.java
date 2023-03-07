package com.techmarket.app.service;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User getUserName(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUserByRole(String role) {
        return userRepository.findByRoles(role);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
