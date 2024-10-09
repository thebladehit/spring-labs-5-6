package spring.labs.dating.user.projections;

public interface UserProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    int getAge();
    String getStatus();
}
