package spring.labs.dating.user.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
}
