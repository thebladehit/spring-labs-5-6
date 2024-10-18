package spring.labs.dating.secretInfo.dto;

import jakarta.validation.constraints.NotBlank;

public record SecretDto (
    @NotBlank(message = "Secret cannot be empty")
    String secret
) {}
