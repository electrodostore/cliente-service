package com.electrodostore.cliente_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteRequestDto(
        @NotBlank String name,
        @NotBlank String cellphone,
        @NotBlank @Pattern(regexp = "\\d+") String document,
        @NotBlank String address
) {}
