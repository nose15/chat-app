package org.lukas.chat.wscommunication;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IncomingMessage {
    private UUID chatId;
    private UUID creatorId;
    private String content;
}
