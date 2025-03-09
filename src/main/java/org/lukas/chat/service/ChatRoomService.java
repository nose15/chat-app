package org.lukas.chat.service;

import org.lukas.chat.exception.ForbiddenException;
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

    public void createChatRoom(String name, List<UUID> userIds, UserModel caller) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setAdmin(caller);
        chatRoom.setName(name);
        chatRoom.setMembers(userService.getUsersByIds(userIds));

        chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    public List<ChatRoom> getChatRoomsOfUser(UserModel caller) {
        return chatRoomRepository.findChatRoomsByMembersContaining(caller);
    }

    public ChatRoom getChatRoom(UUID id, UserModel caller) {
        Optional<ChatRoom> result = chatRoomRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No chatroom with id " + id);
        }

        ChatRoom chatRoom = result.get();

        if (caller.getRoles().stream().anyMatch(e -> e.getName().equals("ADMIN"))) {
            return chatRoom;
        }

        if (chatRoom.getMembers().stream().noneMatch(e -> e.equals(caller))) {
            throw new ForbiddenException();
        }

        return chatRoom;
    }

    public void deleteChatRoom(UUID id, UserModel caller) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No chatroom with id " + id));

        if (!chatRoom.getAdmin().equals(caller)) {
            throw new ForbiddenException();
        }

        chatRoomRepository.deleteById(id);
    }

    public void addUserToChatRoom(UUID chatId, UUID userId, UserModel caller) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(
                () -> new ResourceNotFoundException("No chatroom with id " + chatId));

        if (!chatRoom.getAdmin().equals(caller)) {
            throw new ForbiddenException();
        }

        List<UserModel> newMembers = chatRoom.getMembers();
        if (newMembers.stream().anyMatch(e -> e.getId().equals(userId))) {
            return;
        }

        UserModel userModel = userService.getById(userId);
        newMembers.add(userModel);
        chatRoom.setMembers(newMembers);
        chatRoomRepository.save(chatRoom);
    }

    public void removeUserFromChatRoom(UUID chatId, UUID userId, UserModel caller) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElseThrow(
                () -> new ResourceNotFoundException("No chatroom with id " + chatId));

        if (!chatRoom.getAdmin().equals(caller)) {
            throw new ForbiddenException();
        }

        List<UserModel> newMembers = chatRoom.getMembers();
        if (newMembers.stream().noneMatch(e -> e.getId().equals(userId))) {
            throw new ResourceNotFoundException("User with id " + userId + " is not a member of chatroom " + chatId);
        }

        newMembers.removeIf(e -> e.getId().equals(userId));
        chatRoom.setMembers(newMembers);
        chatRoomRepository.save(chatRoom);
    }
}
