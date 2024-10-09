package spring.labs.dating.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
    @Email
    @NotBlank(message = "Email cannot be empty")
    String email,

    @Size(min = 8)
    @NotBlank(message = "Password cannot be empty")
    String password,

    @Min(value = 18, message = "Age must be greater than 18")
    int age,

    @NotBlank(message = "First name cannot be empty")
    String firstName,

    @NotBlank(message = "Last name cannot be empty")
    String lastName,

    @NotBlank(message = "Status cannot be empty")
    String status
) {}
