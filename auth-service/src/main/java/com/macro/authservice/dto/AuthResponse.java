package com.macro.authservice.dto;

import lombok.*;

@Builder
@Setter
@Getter
public class AuthResponse {
    private String userName;
    private String jwt;
}
