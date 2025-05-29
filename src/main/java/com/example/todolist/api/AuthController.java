package com.example.todolist.api;

import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import com.example.todolist.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // DTO для логіна
    public static class AuthRequest {
        public String username;
        public String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            User user = userService.loginUser(authRequest.getUsername(), authRequest.getPassword());
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok().body("Bearer " + token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Невірні дані для входу");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        try {
            userService.registerUser(authRequest.getUsername(), authRequest.getPassword());
            return ResponseEntity.ok("Реєстрація успішна");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Користувач вже існує");
        }
    }
}
