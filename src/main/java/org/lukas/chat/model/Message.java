package org.lukas.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Message {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserModel author;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Column(name = "content")
    private String content;
}
