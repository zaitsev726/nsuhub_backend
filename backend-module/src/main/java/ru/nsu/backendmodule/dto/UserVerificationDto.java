package ru.nsu.backendmodule.dto;

import javax.validation.constraints.NotBlank;

public record UserVerificationDto(@NotBlank String emailOrPhone, @NotBlank String code) {
}
