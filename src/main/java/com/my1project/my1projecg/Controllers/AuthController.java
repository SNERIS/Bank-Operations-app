package com.my1project.my1projecg.Controllers;

import com.my1project.my1projecg.security.JwtService;
import com.my1project.my1projecg.entity.User;
import com.my1project.my1projecg.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
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

        // 1. Gjejmë userin ne DB
        User user = userRepository.findByUsername(username)
                .orElse(null);

        // 2. Kontrollojmë nëse useri ekziston dhe nëse password-i i dhënë përputhet me atë të enkriptuar në DB
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 3. Gjenerojmë token-in
            String token = jwtService.generateToken(username);
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(Duration.ofHours(24))
                    .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(Map.of(
                                "message", "Login Success",
                                "username", username
                        ));

        }

        return ResponseEntity.status(401).body("Username ose Password gabim!");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // false në localhost pa HTTPS
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ZERO)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logout successful");
    }
}