package org.lukas.chat.controller;

import org.lukas.chat.dto.RegisterRequest;
import org.lukas.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    private ResponseEntity<Void> createUser(@RequestBody RegisterRequest reqBody) {
        userService.createUser(reqBody.getEmail(), reqBody.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
