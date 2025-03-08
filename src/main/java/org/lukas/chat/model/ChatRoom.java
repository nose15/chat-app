package org.lukas.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    private UUID id;

    @ManyToMany
    @JoinTable(name = "user_room")
    private List<UserModel> members;
}
