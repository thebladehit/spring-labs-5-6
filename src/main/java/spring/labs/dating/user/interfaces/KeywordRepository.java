package spring.labs.dating.user.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.labs.dating.user.models.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByTitle(String title);
}
