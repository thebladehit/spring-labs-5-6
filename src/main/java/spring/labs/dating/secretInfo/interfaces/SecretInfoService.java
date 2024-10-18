package spring.labs.dating.secretInfo.interfaces;

import spring.labs.dating.secretInfo.dto.SecretDto;
import spring.labs.dating.secretInfo.models.SecretInfo;
import spring.labs.dating.secretInfo.models.SecretInfoAccess;
import spring.labs.dating.user.models.User;

import java.util.List;

public interface SecretInfoService {
    SecretInfo getSecretInfo(Long userId, Long curUserId);
    SecretInfo createSecretInfo(SecretDto secretDto, User user);
    SecretInfo updateSecretInfo(SecretDto secretInfo, Long userId);
    void deleteSecretInfo(Long userId);
    void createRequestForSecretInfo(User userRequester, Long userIdOwner);
    List<SecretInfoAccess> getRequestForSecretInfo(Long userId);
    void updateRequestStatus(Long secretAccessId, boolean status, Long userId);
}
