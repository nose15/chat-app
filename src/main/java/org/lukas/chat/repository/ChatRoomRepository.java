package org.lukas.chat.repository;

import org.lukas.chat.model.ChatRoom;
import org.lukas.chat.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    List<ChatRoom> findChatRoomsByMembersContaining(UserModel userModel);
}
