package spring.labs.dating.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.labs.dating.user.interfaces.UserService;
import spring.labs.dating.user.projections.UserProjection;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    List<UserProjection> getAll() {
        return userService.getALlUsers();
    }

    @GetMapping("/{id}")
    UserProjection getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
