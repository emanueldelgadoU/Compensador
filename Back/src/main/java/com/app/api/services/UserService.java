package com.app.api.services;


import com.app.api.models.UserModel;
import com.app.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticateUser(String username, String password) {
        UserModel user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    public Long getUserId(String username) {
        UserModel user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public UserModel obtenerUsuarioPorId(Long id) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }
}
