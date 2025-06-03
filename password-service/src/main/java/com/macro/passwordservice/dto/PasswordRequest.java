package com.macro.passwordservice.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;


@Data
public class PasswordRequest {
    @NotBlank
    private String serviceName;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
