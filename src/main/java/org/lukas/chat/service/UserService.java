package org.lukas.chat.service;

import org.lukas.chat.model.UserModel;
import org.lukas.chat.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


    public List<UserModel> getUsersByIds(List<UUID> ids) {
        List<UserModel> users = new LinkedList<>();

        for (UUID id : ids) {
            Optional<UserModel> userModel = userRepository.findById(id);

            if (userModel.isEmpty()) {
                throw new IllegalArgumentException("No user with id " + id);
            }

            users.add(userModel.get());
        }

        return users;
    }

    public Optional<UserModel> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
