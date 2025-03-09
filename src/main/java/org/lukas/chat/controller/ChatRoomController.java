package org.lukas.chat.controller;

import org.lukas.chat.dto.CreateChatRoomRequest;
import org.lukas.chat.model.ChatRoom;
import org.lukas.chat.model.UserModel;
import org.lukas.chat.service.ChatRoomService;
import org.lukas.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService, UserService userService) {
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createChatRoom(@RequestBody CreateChatRoomRequest reqBody) {
        chatRoomService.createChatRoom(reqBody.getName(), reqBody.getUserIds());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoom>> getChatRooms(Principal principal) {
        UserModel user = userService.getByEmail(principal.getName());

        if (user.getRoles().stream().anyMatch(e -> e.getName().equals("ADMIN"))) {
             return new ResponseEntity<>(chatRoomService.getAllChatRooms(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(chatRoomService.getChatRoomsOfUser(user), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoom> getChatRoom(@PathVariable UUID id, Principal principal) {
        UserModel userModel = userService.getByEmail(principal.getName());
        return new ResponseEntity<>(chatRoomService.getChatRoom(id, userModel), HttpStatus.OK);
    }

    // remove chatroom

    // add user to chatroom
    // remove user from chatroom

    // retrieve messages from chatroom
}
