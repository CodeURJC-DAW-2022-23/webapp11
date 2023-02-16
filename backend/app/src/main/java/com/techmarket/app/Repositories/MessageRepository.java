package com.techmarket.app.Repositories;

import java.util.List;
import com.techmarket.app.model.Message;

public interface MessageRepository extends JpaRepository<Message, String> {

    // Message findByMessageId(String messageId);
    // Not really sure if we need this one
    List<Message> findByUserId(String userId);

    // List<Message> findByStatus(boolean status);
    // Not really sure if we need this one
}