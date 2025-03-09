package org.lukas.chat.controller;

import org.lukas.chat.dto.AddUserToChatRequest;
import org.lukas.chat.dto.CreateChatRoomRequest;
import org.lukas.chat.model.ChatRoom;
import org.lukas.chat.model.Message;
import org.lukas.chat.model.UserModel;
import org.lukas.chat.service.ChatRoomService;
import org.lukas.chat.service.MessageService;
import org.lukas.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService, MessageService messageService, UserService userService) {
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createChatRoom(@RequestBody CreateChatRoomRequest reqBody, Principal principal) {
        UserModel caller = userService.getByEmail(principal.getName());
        chatRoomService.createChatRoom(reqBody.getName(), reqBody.getUserIds(), caller);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoom>> getChatRooms(Principal principal) {
        UserModel caller = userService.getByEmail(principal.getName());

        if (caller.getRoles().stream().anyMatch(e -> e.getName().equals("ADMIN"))) {
             return new ResponseEntity<>(chatRoomService.getAllChatRooms(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(chatRoomService.getChatRoomsOfUser(caller), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoom> getChatRoom(@PathVariable UUID id, Principal principal) {
        UserModel caller = userService.getByEmail(principal.getName());
        return new ResponseEntity<>(chatRoomService.getChatRoom(id, caller), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable UUID id, Principal principal) {
        UserModel caller = userService.getByEmail(principal.getName());
        chatRoomService.deleteChatRoom(id, caller);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/users")
    public ResponseEntity<Void> addUserToChatRoom(@PathVariable UUID id, Principal principal, @RequestBody AddUserToChatRequest reqBody) {
        UserModel caller = userService.getByEmail(principal.getName());
        chatRoomService.addUserToChatRoom(id, reqBody.getUserId(), caller);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<ChatRoom> removeUserFromChatRoom(@PathVariable UUID id, @PathVariable UUID userId, Principal principal) {
        UserModel caller = userService.getByEmail(principal.getName());
        chatRoomService.removeUserFromChatRoom(id, userId, caller);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<Message>> getChatRoomMessages(@PathVariable UUID id, Principal principal) {
        UserModel caller = userService.getByEmail(principal.getName());
        List<Message> messages = messageService.getChatRoomMessages(id, caller);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
