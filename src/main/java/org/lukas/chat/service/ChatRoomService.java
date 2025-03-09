package org.lukas.chat.service;

import org.lukas.chat.exception.ResourceForbiddenException;
import org.lukas.chat.exception.ResourceNotFoundException;
import org.lukas.chat.model.ChatRoom;
import org.lukas.chat.model.UserModel;
import org.lukas.chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public ChatRoom getChatRoom(UUID id, UserModel user) {
        Optional<ChatRoom> result = chatRoomRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No chatroom with id " + id);
        }

        ChatRoom chatRoom = result.get();

        if (user.getRoles().stream().anyMatch(e -> e.getName().equals("ADMIN"))) {
            return chatRoom;
        }

        if (chatRoom.getMembers().stream().noneMatch(e -> e.equals(user))) {
            throw new ResourceForbiddenException();
        }

        return chatRoom;
    }
}
