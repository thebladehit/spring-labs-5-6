package spring.labs.dating.auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.labs.dating.auth.interfaces.AuthService;
import spring.labs.dating.auth.dto.LoginDTO;
import spring.labs.dating.auth.dto.RegisterDTO;
import spring.labs.dating.user.models.User;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO userDTO) {
        return new ResponseEntity<>(authService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(authService.login(loginDTO.email(), loginDTO.password()), HttpStatus.OK);
    }
}
