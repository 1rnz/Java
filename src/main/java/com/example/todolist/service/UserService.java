package com.example.todolist.service;

import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Реєстрація нового користувача
    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return null; // Користувач уже існує
        }
        return userRepository.save(new User(username, password));
    }

    // Перевірка логіна
    public User loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.filter(u -> u.getPassword().equals(password)).orElse(null);
    }
}
