package com.techmarket.app.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private static UserSession instance;
    private String username;
    private String password;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
