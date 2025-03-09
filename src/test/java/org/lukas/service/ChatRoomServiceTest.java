package org.lukas.service;

import org.junit.jupiter.api.Test;
import org.lukas.chat.service.ChatRoomService;
import org.lukas.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class ChatRoomServiceTest {
    @Autowired
    ChatRoomService chatRoomService;

    @MockitoBean
    UserService userService;
}
