package spring.labs.dating.security;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenPublisher {
    List<String> tokens = new ArrayList<>();

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        tokens.add(token);
        return token;
    }

    public boolean validateToken(String token) {
        return tokens.contains(token);
    }
}
