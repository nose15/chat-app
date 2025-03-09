package org.lukas.chat.service;

import org.lukas.chat.exception.ForbiddenException;
import org.lukas.chat.model.ChatRoom;
import org.lukas.chat.model.Message;
import org.lukas.chat.model.UserModel;
import org.lukas.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRoomService chatRoomService) {
        this.messageRepository = messageRepository;
        this.chatRoomService = chatRoomService;
    }

    public List<Message> getChatRoomMessages(UUID chatId, UserModel caller) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatId, caller);

        if (chatRoom.getMembers().stream().noneMatch(e -> e.equals(caller))) {
            throw new ForbiddenException();
        }

        return messageRepository.getMessagesByChatRoom_Id(chatId);
    }
}
