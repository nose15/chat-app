package org.lukas.chat.controller;

import org.lukas.chat.dto.CreateChatRoomRequest;
import org.lukas.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping
    private ResponseEntity<Void> createChatroom(@RequestBody CreateChatRoomRequest reqBody) {
        chatRoomService.createChatRoom(reqBody.getName(), reqBody.getUserIds());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // retrieve all user's chatrooms metadata
    // retrieve one chatroom metadata
    // remove chatroom

    // add user to chatroom
    // remove user from chatroom

    // retrieve messages from chatroom
}
