package com.techmarket.app.service;

import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getEncodedPassword(), roles);
    }

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

    public User getCurrentUser(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return getUserName(email);
    }
}
