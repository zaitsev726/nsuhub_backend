package ru.nsu.backendmodule.dto;

import javax.validation.constraints.NotBlank;

public record UserVerificationRequestDto(@NotBlank String phoneOrEmail) {
}
