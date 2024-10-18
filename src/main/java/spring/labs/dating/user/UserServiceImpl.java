package spring.labs.dating.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import spring.labs.dating.auth.dto.RegisterDTO;
import spring.labs.dating.user.dto.UpdateUserDTO;
import spring.labs.dating.user.interfaces.KeywordRepository;
import spring.labs.dating.user.interfaces.UserRepository;
import spring.labs.dating.user.interfaces.UserService;
import spring.labs.dating.user.models.Keyword;
import spring.labs.dating.user.models.User;
import spring.labs.dating.user.projections.UserProjection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;

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

    @Transactional
    @Override
    public UserProjection updateUser(User user, UpdateUserDTO userDTO) {
        user.setAge(userDTO.age());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setStatus(userDTO.status());

        List<Keyword> keywords = userDTO.keywords().stream()
            .map(this::findOrCreateKeyword)
            .toList();
        user.setKeywords(keywords);

        userRepository.save(user);

        return getUserById(user.getId());
    }

    @Override
    public Keyword findOrCreateKeyword(String keywordTitle) {
        Keyword keyword = keywordRepository.findByTitle(keywordTitle);

        if (keyword != null) return keyword;

        Keyword newKeyword = new Keyword();
        newKeyword.setTitle(keywordTitle);
        return keywordRepository.save(newKeyword);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
