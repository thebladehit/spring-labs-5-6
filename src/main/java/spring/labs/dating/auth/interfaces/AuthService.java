package spring.labs.dating.auth.interfaces;

import spring.labs.dating.auth.dto.RegisterDTO;
import spring.labs.dating.user.models.User;

public interface AuthService {
    public User registerUser(RegisterDTO user);
    public String login(String email, String password);
}
