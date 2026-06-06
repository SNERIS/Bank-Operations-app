package com.my1project.my1projecg.Controllers;

import com.my1project.my1projecg.security.JwtService;
import com.my1project.my1projecg.entity.User;
import com.my1project.my1projecg.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // 1. Gjejmë userin në DB
        User user = userRepository.findByUsername(username)
                .orElse(null);

        // 2. Kontrollojmë nëse useri ekziston dhe nëse password-i i dhënë përputhet me atë të enkriptuar në DB
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 3. Gjenerojmë token-in
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Username ose Password gabim!");
    }
}