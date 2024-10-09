package spring.labs.dating.user.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private int age;
    private String firstName;
    private String lastName;
    private String status;

    @Column(unique = true)
    private String email;
    private String password;
}
