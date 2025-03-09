package org.lukas.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddUserToChatRequest {
    private UUID userId;
}
