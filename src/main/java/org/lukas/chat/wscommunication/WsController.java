package org.lukas.chat.wscommunication;

import jakarta.websocket.OnMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage sendChatMessage(IncomingMessage incomingMessage) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatId(incomingMessage.getChatId());
        chatMessage.setCreatorId(incomingMessage.getCreatorId());
        chatMessage.setContent(incomingMessage.getContent());

        return chatMessage;
    }

}
