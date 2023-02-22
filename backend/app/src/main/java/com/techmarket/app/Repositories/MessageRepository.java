package com.techmarket.app.Repositories;

import java.util.List;
import com.techmarket.app.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MessageRepository extends JpaRepository<Message, String> {

    // Message findByMessageId(String messageId);
    // Not really sure if we need this one
    List<Message> findByUserId(String userId);

    // List<Message> findByStatus(boolean status);
    // Not really sure if we need this one
}