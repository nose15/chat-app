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
public class UserModelService {
    private final UserModelRepository userModelRepository;

    @Autowired
    public UserModelService(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    public List<UserModel> getUsersByIds(List<UUID> ids) {
        List<UserModel> users = new LinkedList<>();

        for (UUID id : ids) {
            Optional<UserModel> userModel = userModelRepository.findById(id);

            if (userModel.isEmpty()) {
                throw new IllegalArgumentException("No user with id " + id);
            }

            users.add(userModel.get());
        }

        return users;
    }
}
