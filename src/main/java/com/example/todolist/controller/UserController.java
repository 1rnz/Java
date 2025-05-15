package com.example.todolist.controller;

import jakarta.servlet.http.HttpSession;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Форма реєстрації
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // Обробка реєстрації
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.registerUser(username, password);
        if (user == null) {
            model.addAttribute("error", "Користувач з таким ім'ям вже існує!");
            return "register";
        }
        return "redirect:/user/login";
    }

    // Форма логіна
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Обробка логіна
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.loginUser(username, password);
        if (user == null) {
            model.addAttribute("error", "Невірний логін або пароль!");
            return "login";
        }
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());

        return "redirect:/tasks"; // Перенаправлення на список задач
    }

    // Вихід з акаунту
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
