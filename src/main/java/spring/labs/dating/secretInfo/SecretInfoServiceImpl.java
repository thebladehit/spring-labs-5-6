package spring.labs.dating.secretInfo;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import spring.labs.dating.secretInfo.dto.SecretDto;
import spring.labs.dating.secretInfo.interfaces.SecretInfoAccessRepository;
import spring.labs.dating.secretInfo.interfaces.SecretInfoRepository;
import spring.labs.dating.secretInfo.interfaces.SecretInfoService;
import spring.labs.dating.secretInfo.models.SecretInfo;
import spring.labs.dating.secretInfo.models.SecretInfoAccess;
import spring.labs.dating.user.models.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecretInfoServiceImpl implements SecretInfoService {
    private final SecretInfoRepository secretInfoRepository;
    private final SecretInfoAccessRepository secretInfoAccessRepository;

    @Override
    public SecretInfo getSecretInfo(Long userId, Long curUserId) {
        SecretInfo secretInfo = secretInfoRepository.findByUserId(userId);
        if (secretInfo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (secretInfo.getUser().getId().equals(curUserId)) return secretInfo;
        else {
            SecretInfoAccess secretInfoAccess = secretInfoAccessRepository.findByUserId(curUserId);
            if (secretInfoAccess == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have not requested this resource or it doesn't exist");
            if (!secretInfoAccess.isPermitted()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this resource");
            return secretInfo;
        }
    }

    @Override
    public SecretInfo createSecretInfo(SecretDto secretDto, User user) {
        SecretInfo secretInfo = new SecretInfo();
        secretInfo.setSecret(secretDto.secret());
        secretInfo.setUser(user);
        return secretInfoRepository.save(secretInfo);
    }

    @Override
    public SecretInfo updateSecretInfo(SecretDto secretInfo, Long userId) {
        SecretInfo secretInfoExist = secretInfoRepository.findByUserId(userId);
        if (secretInfoExist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have a secret info");
        secretInfoExist.setSecret(secretInfo.secret());
        return secretInfoRepository.save(secretInfoExist);
    }

    @Override
    public void deleteSecretInfo(Long userId) {
        SecretInfo secretInfo = secretInfoRepository.findByUserId(userId);
        if (secretInfo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have a secret info");
        secretInfoRepository.delete(secretInfo);
    }

    @Override
    public void createRequestForSecretInfo(User userRequester, Long userIdOwner) {
        if (Objects.equals(userRequester.getId(), userIdOwner)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't create resource for you secret");
        }
        SecretInfo secretInfo = secretInfoRepository.findByUserId(userIdOwner);
        if (secretInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id = "+ userIdOwner +" don't have a secret info");
        }
        SecretInfoAccess secretInfoAccessExist = secretInfoAccessRepository.findBySecretInfoIdAndUserId(secretInfo.getId(), userRequester.getId());
        if (secretInfoAccessExist != null) throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already created this request");
        SecretInfoAccess secretInfoAccess = new SecretInfoAccess();
        secretInfoAccess.setSecretInfo(secretInfo);
        secretInfoAccess.setUser(userRequester);
        secretInfoAccessRepository.save(secretInfoAccess);
    }

    @Override
    public List<SecretInfoAccess> getRequestForSecretInfo(Long userId) {
        SecretInfo secretInfo = secretInfoRepository.findByUserId(userId);
        System.out.println(userId);
        if (secretInfo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have a secret info");
        return secretInfoAccessRepository.findAllBySecretInfoId(secretInfo.getId());
    }


    @Override
    public void updateRequestStatus(Long secretAccessId, boolean status, Long userId) {
        Optional<SecretInfoAccess> secretInfoAccess = secretInfoAccessRepository.findById(secretAccessId);
        if (secretInfoAccess.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such secret info");
        SecretInfoAccess secretInfoAccessExist = secretInfoAccess.get();
        if (!secretInfoAccessExist.getSecretInfo().getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't change status of this secret");
        }
        secretInfoAccessExist.setPermitted(status);
        secretInfoAccessRepository.save(secretInfoAccessExist);
    }
}
