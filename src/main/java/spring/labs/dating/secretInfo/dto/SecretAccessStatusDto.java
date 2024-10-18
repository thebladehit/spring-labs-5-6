package spring.labs.dating.secretInfo.dto;

import jakarta.validation.constraints.NotNull;

public record SecretAccessStatusDto (
        @NotNull(message = "Status cannot be empty")
        boolean status
) {}
