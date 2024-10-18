package spring.labs.dating.secretInfo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.labs.dating.secretInfo.models.SecretInfoAccess;

import java.util.List;

public interface SecretInfoAccessRepository extends JpaRepository<SecretInfoAccess, Long> {
    List<SecretInfoAccess> findAllBySecretInfoId(Long userId);
    SecretInfoAccess findByUserId(Long userId);
    SecretInfoAccess findBySecretInfoIdAndUserId(Long secretInfo_id, Long user_id);
}
