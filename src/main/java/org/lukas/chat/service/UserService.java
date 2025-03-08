package org.lukas.chat.service;

import org.lukas.chat.model.UserModel;
import org.lukas.chat.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserModelRepository userRepository;

    @Autowired
    public UserService(UserModelRepository userModelRepository) {
        this.userRepository = userModelRepository;
    }

    public void createUser(String email, String password) {
        UserModel newUser = new UserModel();
        newUser.setEmail(email);
        newUser.setPassword(password);

        userRepository.save(newUser);
    }
}
