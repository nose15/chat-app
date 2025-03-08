package org.lukas.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    private String email;
    private String password;
}
