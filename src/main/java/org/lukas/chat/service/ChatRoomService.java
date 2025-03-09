package org.lukas.chat.service;

import org.lukas.chat.model.ChatRoom;
import org.lukas.chat.model.UserModel;
import org.lukas.chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomService {
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomService(UserService userService, ChatRoomRepository chatRoomRepository) {
        this.userService = userService;
        this.chatRoomRepository = chatRoomRepository;
    }

    public void createChatRoom(String name, List<UUID> userIds) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setMembers(userService.getUsersByIds(userIds));

        chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    public List<ChatRoom> getChatRoomsOfUser(UserModel user) {
        return chatRoomRepository.findChatRoomsByMembersContaining(user);
    }
}
