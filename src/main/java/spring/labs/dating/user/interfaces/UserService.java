package spring.labs.dating.user.interfaces;

import spring.labs.dating.auth.dto.RegisterDTO;
import spring.labs.dating.user.dto.UpdateUserDTO;
import spring.labs.dating.user.models.Keyword;
import spring.labs.dating.user.models.User;
import spring.labs.dating.user.projections.UserProjection;

import java.util.List;

public interface UserService {
    public User createUser(RegisterDTO userDTO);
    public User getUserByEmail(String email);
    public UserProjection getUserById(Long id);
    public List<UserProjection> getALlUsers();
    public UserProjection updateUser(User user, UpdateUserDTO userDTO);
    public Keyword findOrCreateKeyword(String keywordTitle);
    public void deleteUser(Long id);
}
