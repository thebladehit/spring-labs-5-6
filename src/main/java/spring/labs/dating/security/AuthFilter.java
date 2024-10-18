package spring.labs.dating.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import spring.labs.dating.user.interfaces.UserRepository;
import spring.labs.dating.user.models.User;

import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthFilter implements Filter {

    private final TokenPublisher tokenPublisher;
    private final UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (path.startsWith("/api/v1/auth") || !path.startsWith("/api/v1")) {
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userEmail = tokenPublisher.validateToken(token);
            if (userEmail == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
            User user = userRepository.findByEmail(userEmail);
            httpRequest.setAttribute("user", user);
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not authenticated");
    }
}
