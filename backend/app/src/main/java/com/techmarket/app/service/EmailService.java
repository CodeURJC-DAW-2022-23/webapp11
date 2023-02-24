package com.techmarket.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendAccountConfirmationEmail(String email, String name) throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
        // Set who the email is from
        message.setFrom("threetechmarket@outlook.com");
        message.setTo(email);
        message.setSubject("Welcome to 3TechMarket!");
        // Use our html template, this is hideous, but it works and I have to figure out how to do it properly using Mustache
        message.setText("<body style=\"font-family: Verdana, Geneva, Tahoma, sans-serif; background-color: #181A1B; color: whitesmoke\">\n" +
                "\n" +
                "<center><img  style=\"padding-top: 15px\" src=\"https://i.imgur.com/e8DsdaG.png\" alt=\"logo\" width=\"180\" height=\"76\"></center>\n" +
                "\n" +
                "<h1 style=\"text-align: center; padding-top: 25px; color: #7aca3d\">Account Created Successfully</h1>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <p>Hi " + name + ",</p>\n" +
                "    <p>Your account has been created successfully.</p>\n" +
                "    <p> You can log in using your email: " + email + " and the password you configured.</p>\n" +
                "    <p>Thank you for joining 3TechMarket!</p>\n" +
                "    <p>Best regards,</p>\n" +
                "    <p>3TechMarket Team</p>\n" +
                "</div>\n" +
                "<br>\n" +
                "<footer style=\"text-align: center\">\n" +
                "    <p>You are receiving this email because you have registered an account with 3TechMarket.</p>\n" +
                "    <p>Do not reply to this email. This mailbox is not monitored, and you will not receive a response.</p>\n" +
                "</footer>\n" +
                "\n" +
                "</body>\n" +
                "</html>", true);
        javaMailSender.send(message.getMimeMessage());
    }
}
