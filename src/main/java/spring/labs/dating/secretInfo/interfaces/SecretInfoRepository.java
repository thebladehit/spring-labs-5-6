package spring.labs.dating.secretInfo.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import spring.labs.dating.secretInfo.models.SecretInfo;

public interface SecretInfoRepository extends JpaRepository<SecretInfo, Long> {
    SecretInfo findByUserId(Long user_id);
}
