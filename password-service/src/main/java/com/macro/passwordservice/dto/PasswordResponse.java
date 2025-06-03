package com.macro.passwordservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PasswordResponse {
    private UUID id;
    private String userEmail;
    private String serviceName;
    private String userName;
    private String password;
    private LocalDateTime updatedAt;
}
