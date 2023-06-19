package account.controllers;

import account.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {



    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody User user) {
        User testUser = null;
        testUser = user;

        if (testUser == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().body(testUser);
        }
    }
}
