package spring.labs.dating.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO (
    @Email
    @NotBlank(message = "Email cannot be empty")
    String email,

    @Size(min = 8)
    @NotBlank(message = "Repeat password cannot be empty")
    String password
) {}
