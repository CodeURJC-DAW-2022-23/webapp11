package com.techmarket.app.Repositories;

import com.techmarket.app.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface MessageRepository extends JpaRepository<Message, String> {

    // Message findByMessageId(String messageId);
    // Not really sure if we need this one
    List<Message> findByUserId(Long userId);

    // List<Message> findByStatus(boolean status);
    // Not really sure if we need this one
}