package spring.labs.dating.auth;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import spring.labs.dating.auth.interfaces.AuthService;
import spring.labs.dating.security.TokenPublisher;
import spring.labs.dating.auth.dto.RegisterDTO;
import spring.labs.dating.user.interfaces.UserService;
import spring.labs.dating.user.models.User;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final TokenPublisher tokenPublisher;

    @Override
    @Transactional
    public User registerUser(RegisterDTO user) {
        String email = user.email();
        User existingUser = userService.getUserByEmail(email);
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email " + email + " already exists");
        }
        return userService.createUser(user);
    }

    @Override
    public String login(String email, String password) {
        User user = userService.getUserByEmail(email);
        System.out.println("here");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email " + email + " doesn't exists");
        }
        if (!Objects.equals(user.getPassword(), password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        return tokenPublisher.generateToken(email);
    }
}
