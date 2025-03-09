package org.lukas.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateChatRoomRequest {
    private String name;
    private List<UUID> userIds;
}
