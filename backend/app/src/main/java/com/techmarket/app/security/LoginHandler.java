package com.techmarket.app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);

    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null){
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl = determineTargetUrl(authentication);
        try {
            response.sendRedirect(targetUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String determineTargetUrl(Authentication authentication) {
        String url = "/";

        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"))) {
            url = "/dashboard";
        } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("USER"))) {
            url = "/";
        } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("AGENT"))) {
            url = "/chats";
        }

        return url;
    }
}