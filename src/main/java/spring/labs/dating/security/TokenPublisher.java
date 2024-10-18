package spring.labs.dating.security;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenPublisher {
    HashMap<String, String> tokens = new HashMap<>();

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        tokens.put(token, email);
        return token;
    }

    public String validateToken(String token) {
        return tokens.get(token);
    }
}
