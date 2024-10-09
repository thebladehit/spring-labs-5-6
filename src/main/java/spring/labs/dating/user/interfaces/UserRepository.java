package spring.labs.dating.user.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.labs.dating.user.models.User;
import spring.labs.dating.user.projections.UserProjection;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u")
    List<UserProjection> findAllWithoutPassword();

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    UserProjection findUserById(Long id);
}
