package spring.labs.dating.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.labs.dating.auth.dto.RegisterDTO;
import spring.labs.dating.user.interfaces.UserRepository;
import spring.labs.dating.user.interfaces.UserService;
import spring.labs.dating.user.models.User;
import spring.labs.dating.user.projections.UserProjection;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(RegisterDTO userDTO) {
        User user = new User();
        user.setAge(userDTO.age());
        user.setEmail(userDTO.email());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setPassword(userDTO.password());
        user.setStatus(userDTO.status());
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserProjection getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public List<UserProjection> getALlUsers() {
        return userRepository.findAllWithoutPassword();
    }
}
