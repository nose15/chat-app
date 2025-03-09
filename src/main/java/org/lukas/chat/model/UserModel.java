package org.lukas.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_roles")
    @JsonIgnore
    private List<Role> roles;

    @ManyToMany(mappedBy = "members")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ChatRoom> chatRooms;
}
