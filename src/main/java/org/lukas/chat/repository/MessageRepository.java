package org.lukas.chat.repository;

import org.lukas.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    public List<Message> getMessagesByChatRoom_Id(UUID chatId);
}
