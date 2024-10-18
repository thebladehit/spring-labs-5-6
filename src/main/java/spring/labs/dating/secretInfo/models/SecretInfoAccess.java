package spring.labs.dating.secretInfo.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import spring.labs.dating.user.models.User;

@Data
@Entity
@Table(name = "secret_info_access")
public class SecretInfoAccess {
    @Id
    @GeneratedValue
    private Long id;

    private boolean isPermitted = false;

    @ManyToOne
    @JoinColumn(name = "secret_id")
    private SecretInfo secretInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
