package spring.labs.dating.user.projections;

import spring.labs.dating.user.models.Keyword;

import java.util.List;

public interface UserProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    int getAge();
    String getStatus();
    List<Keyword> getKeywords();
}
