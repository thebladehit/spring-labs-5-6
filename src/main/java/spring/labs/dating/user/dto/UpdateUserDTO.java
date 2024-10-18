package spring.labs.dating.user.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record UpdateUserDTO (
    @Min(value = 18, message = "Age must be greater than 18")
    int age,

    @NotBlank(message = "First name cannot be empty")
    String firstName,

    @NotBlank(message = "Last name cannot be empty")
    String lastName,

    @NotBlank(message = "Status cannot be empty")
    String status,

    @NotEmpty(message = "The list of keywords must not be empty")
    List<@NotEmpty(message = "Keyword in the keywords cannot be empty") String> keywords
) {}
