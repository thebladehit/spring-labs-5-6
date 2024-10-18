package spring.labs.dating.secretInfo.models;

import jakarta.persistence.*;
import lombok.Data;
import spring.labs.dating.user.models.User;

@Data
@Entity
@Table(name = "secret_info")
public class SecretInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String secret;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
