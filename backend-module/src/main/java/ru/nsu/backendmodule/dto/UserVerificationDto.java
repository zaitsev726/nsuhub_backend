package ru.nsu.backendmodule.dto;

import javax.validation.constraints.NotBlank;

public record UserVerificationDto(@NotBlank String credentialToVerify,
                                  @NotBlank String code,
                                  @NotBlank Boolean isEmail) {
}
