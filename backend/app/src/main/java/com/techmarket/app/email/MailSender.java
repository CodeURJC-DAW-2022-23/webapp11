package com.techmarket.app.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSender {

    @Bean
    public JavaMailSender JavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);
        mailSender.setUsername("threetechmarket@outlook.com");
        mailSender.setPassword("FsRh3zQ3mbh7GsS");

        Properties mailProperties = mailSender.getJavaMailProperties();
        mailProperties.put("mail.transport.protocol", "smtp");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.starttls.required", "true");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.office365.com");
        mailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        return mailSender;
    }
}
